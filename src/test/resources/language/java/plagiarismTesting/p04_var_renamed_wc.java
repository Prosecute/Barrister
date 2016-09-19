package language.java.plagiarismTesting;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;



public class Robot {
	public static final short LENGHTWidth= 2_040;	
	public static final int TIMEOUT=100;

	static DatagramChannel datagramChannel;
	static public Thread ThreadRead,ThreadWrite;
    static DatagramSocket datagramSocket;
    static  SocketAddress port_local,port_remote;
     
    static public Connection connection;
    
	static public volatile long TimeRunning=0;
    static public volatile Integer isConnected=null;
    static public volatile boolean isClosing=false,isClosed=false;


    
    public static void sendPacket(Packet packet)
    {
		System.out.print("OUT ");
		packet.writeOut();
		System.out.println();
    	try {
			datagramChannel.send(packet.getAll(), port_remote);
			TimeRunning=System.currentTimeMillis();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public static void closeConnection( boolean input)
    {
		sendPacket(getEndPacket());
		connection.endCon();
		isClosing=true;
    }
    public static void errorConnection(String message,boolean sendPacket)
    {
		System.out.println("Error: "+message);
		if(sendPacket)
			sendPacket(getResetPacket());
		connection.endCon();
		isClosing=true;
		isClosed=true;
    }
	public static void recievePacket(Packet packet)
    {
		System.out.print("IN ");
		packet.writeOut();
		System.out.println();
		if(!packet.isValid())
		{
			return;
		}
		else if(isConnected==null)
		{
			isConnected=packet.getIdent();
		}
		else if(isConnected==packet.getIdent() && packet.getFlag()==Flags.NONE)
			connection.recievePacket(packet);
		else if(isConnected!=packet.getIdent()){
			System.out.print("IN !!!!! Divny packet s spatnym ID !!!!! ");packet.writeOut();System.out.println();
		}
		else if(packet.getFlag()==Flags.RST)
			errorConnection("Prichozi reset packet..",false);
		else if(packet.getFlag()==Flags.FIN)
		{
			if(!isClosing)
				sendPacket(getEndPacket());
			isClosed=true;
			datagramSocket.close();
			connection.endCon();
			System.out.println("Spojeni uspesne uzavreno");
		}
		else
		{
			errorConnection("Nezname pripojeni.",true);
		}
    }
    
	public static void main(String... arguments) throws IOException
	{
		String address;
		int p=4000;
		String path = null;
		if(arguments.length>2)
		{
			System.out.println("Wrong args");
			return;
		}
		if(arguments.length==2)
			path=arguments[1];
		address=arguments[0];
		if(address.contains(":"))
		{
			try {
			p=Integer.parseInt(address.substring(address.indexOf(":")+1));
			} catch (NumberFormatException e)
			{
				System.out.println("Not a port");
				return;
			}
			address=address.substring(0,address.indexOf(":"));
		}
	    port_local = new InetSocketAddress(0); 

		datagramChannel = DatagramChannel.open();
	    datagramSocket=datagramChannel.socket();
	    datagramSocket.setSoTimeout(90000); 
	    datagramSocket.bind(port_local);
	    port_remote=new InetSocketAddress(address,p);
	    datagramChannel.connect(port_remote);

		startpacket.setAcquired((short)0x00);
		startpacket.setSequence((short)0x00);
		startpacket.setIdent(0x0000);
		startpacket.setFlag(Flags.SYN);
	    if(path==null)
	    {
	    	connection=new DownloadConnection();
			startpacket.setData((byte)0x1);
	    }
	    else
	    {
	    	connection=new UploadConnection(path);
			startpacket.setData((byte)0x2);
	    }
	    sendPacket(startpacket);
	    
	    ThreadWrite=new Thread(new WriteThread(connection));
	    ThreadRead=Thread.currentThread();
	    ThreadWrite.start();
	    try {
	    while(!isClosed)
	    {
		    DatagramPacket dp=new DatagramPacket(new byte[264], 264);
	    	datagramSocket.receive(dp);
	    	recievePacket(new Packet(dp.getData(),dp.getOffset(),dp.getLength()));
	    }
	    }
	    catch(ClosedChannelException ex){}
		
	}
	
	public static final Packet startpacket=new Packet(1);

	public static Packet getEndPacket() {
		Packet endpacket=new Packet(0);
		endpacket.setAcquired((connection.getType()==0x2)?(short)0x00:connection.getLastRecSeq());
		endpacket.setSequence((connection.getType()==0x1)?(short)0x00:connection.getLastRecSeq());
		endpacket.setIdent(isConnected);
		endpacket.setFlag(Flags.FIN);
		return endpacket;
	}
	
    public static Packet getResetPacket() {
		Packet resetpacket=new Packet(0);
		resetpacket.setAcquired((short)0x00);
		resetpacket.setSequence((short)0x00);
		if(isConnected!=null)
		resetpacket.setIdent(isConnected);
		resetpacket.setFlag(Flags.RST);
		return resetpacket;
	}
}
abstract class Connection
{
	protected short lastRecieved;
	public short getLastRecSeq()
	{
		return lastRecieved;
	}
	
	protected RandomAccessFile randomFile;
	protected FileChannel fileChannel;
	protected List<Packet> windowForFiles=new ArrayList<>();
	
	public abstract byte getType();
	
	public abstract void endCon();
	
	public abstract void sendPacket() throws IOException;
	public abstract void recievePacket(Packet packet);
	
	}
class UploadConnection extends Connection
{
	private int counted;
	private int acizWindow;
	private volatile short RecieveSeq=0;
	public UploadConnection(String p)
	{

		try {
			randomFile= new RandomAccessFile(p, "rw");
		} catch (FileNotFoundException e) {
			Robot.errorConnection("Chyba pri pristupu k '"+p+"' souboru. Permice?",true);
		}
		fileChannel=randomFile.getChannel();
	}
	@Override
	public void recievePacket(Packet packet) {
		RecieveSeq=packet.getAcquired();
		if(RecieveSeq==lastRecieved)
			counted++;
	}

	public Packet createPacket(int Sequence)
	{
		ByteBuffer bytebuffer=ByteBuffer.allocate(255);
		int i=0;
		try {
			i = fileChannel.read(bytebuffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(i==-1)
		{
			return null;
		}
		Packet packet=new Packet(i);
		packet.setSequence((short)Sequence);
		bytebuffer.position(0);
		bytebuffer.limit(i);
		byte[] ddd=new byte[i];
		bytebuffer.get(ddd, 0,i);
		packet.setData(ddd);
		packet.setIdent(Robot.isConnected);
		return packet;
	}
	public void fillWindowAndSend(boolean cccc) throws IOException
	{
		int time=lastRecieved;

		for(Packet packet:windowForFiles)
		{
			if(!cccc)
				Robot.sendPacket(packet);
			time=packet.createAcquired();
		}
		while(acizWindow+(Math.min(255, fileChannel.size()-fileChannel.position()))<Robot.LENGHTWidth)
		{
			Packet packet=createPacket(time);
			if(packet==null) break;
			time+=packet.getData().capacity()-9;
			acizWindow+=packet.getData().capacity()-9;
			windowForFiles.add(packet);
				Robot.sendPacket(packet);
		}
		if(windowForFiles.size()==0)
			Robot.closeConnection(true);
	}
	public synchronized void sendPacket() throws IOException
	{
		if(RecieveSeq==lastRecieved)
		{
			if(counted!=3 && Robot.TimeRunning+Robot.TIMEOUT>=System.currentTimeMillis()) return;
			counted=0;
			fillWindowAndSend(false);
			return;
		}
		if(RecieveSeq<lastRecieved && RecieveSeq>Short.MIN_VALUE+Robot.LENGHTWidth)
		{
			Robot.closeConnection(false);
			return;
		}
		lastRecieved=RecieveSeq;
		while(!windowForFiles.isEmpty())
		{
			if(windowForFiles.get(0).createAcquired()==lastRecieved)
			{
				acizWindow-=windowForFiles.get(0).getData().remaining();
				windowForFiles.remove(0);
				break;
			}
			acizWindow-=windowForFiles.get(0).getData().remaining();
			windowForFiles.remove(0);
		}
		fillWindowAndSend(true);
		
	}
	
	@Override
	public byte getType() {
		return 0x2;
	}
	@Override
	public void endCon() {
		try {
			randomFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	}
class DownloadConnection extends Connection
{

	@Override
	public void endCon() {
		try {
			randomFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public DownloadConnection()
	{
		try {
			randomFile= new RandomAccessFile("foto.png", "rw");
		} catch (FileNotFoundException e) {
			Robot.errorConnection("Chyba pri pristupu k foto.png souboru. Permice?",true);
		}
		fileChannel=randomFile.getChannel();
	}
	private void saveData(ByteBuffer bytebuffer)
	{
		try {
			while(bytebuffer.hasRemaining())
				fileChannel.write(bytebuffer);
		} catch (IOException e) {
			Robot.errorConnection("Chyba pri zapisu v foto.png souboru. Permice?",true);
		}
	}
	public void recievePacket(Packet packet)
	{
			if(lastRecieved==packet.getSequence())
			{
				saveData(packet.getData());
				lastRecieved=packet.createAcquired();
				int remove=0;
				for(Packet e: windowForFiles)
				{
					if(e.createAcquired()<lastRecieved)
					{
						remove++;
					}
					else if(lastRecieved<Short.MIN_VALUE+Robot.LENGHTWidth && e.createAcquired()>Short.MAX_VALUE-Robot.LENGHTWidth)
					{
						remove++;
					}
					else if(e.getSequence()<=lastRecieved)
					{
						ByteBuffer d=e.getData();
						int t=e.getSequence();
						d.position(9+(lastRecieved-t));
						saveData(d);
						lastRecieved=e.createAcquired();
						remove++;
					}
					else if((lastRecieved<Short.MIN_VALUE+Robot.LENGHTWidth && e.getSequence()>Short.MAX_VALUE-Robot.LENGHTWidth))
					{
						ByteBuffer d=e.getData();
						int t=e.getSequence();
						d.position(9+(lastRecieved-t));
						saveData(d);
						lastRecieved=e.createAcquired();
						remove++;
					}
					else break;
				}
				for(int r=0; r<remove;r++)
					windowForFiles.remove(0);
				Packet packet2=new Packet(0);
				packet2.setIdent(Robot.isConnected);
				packet2.setAcquired((short)lastRecieved);
				Robot.sendPacket(packet2);
			}
			else
			{

				int position=0;
				int sequence=packet.getSequence();
				for(Packet packet2:windowForFiles)
				{
					if(sequence>packet2.getSequence())
					{position++;}
					else if((sequence<Short.MIN_VALUE+Robot.LENGHTWidth && packet2.getSequence()>Short.MIN_VALUE+Robot.LENGHTWidth))
						position++;
					else
					{
						if(sequence!=packet2.getSequence())
							windowForFiles.add(position, packet);
					 break;
					}
				}
				windowForFiles.add(position, packet);
				Packet packet2=new Packet(0);
				packet2.setIdent(Robot.isConnected);
				packet2.setAcquired((short)lastRecieved);
				Robot.sendPacket(packet2);
			}
		}

	@Override
	public byte getType() {
		return 0x1;
	}
	@Override
	public synchronized void sendPacket() throws IOException {
		
	}
}

class Packet {
	ByteBuffer bytebuffer;
	int checkSequence;
	public boolean isValid(){
		if(getFlag()!=Flags.SYN && Robot.isConnected==null)
		{
			Robot.errorConnection("Prichozi packet bez Flagu SYN kdyz neni nastaveno ID spojeni.",true);
			return false;
		}
		if(getFlag()==Flags.SYN)
		{
			if(bytebuffer.capacity()!=10)
			{
				Robot.errorConnection("Chybna delka SYN packetu.",true);
				return false;
			}
			byte bytedata=getData().get();
			if(bytedata!=0x01 && bytedata!=0x02 && getAcquired()!=0 && getSequence()!=0)
			{
				Robot.errorConnection("Chybny kód v SYN packetu.",true);
				return false;
			}
			if(Robot.isConnected!=null)
			{
				System.out.print("IN !!!!! Duplikacni SYN packet zahazuji !!!!! "); writeOut();
				return false;
			}
			return true;
		}
		if (bytebuffer.capacity()>9 && getFlag()!=Flags.NONE)
		{
			Robot.errorConnection("Datovy packet s nastavenym FLAGem.",true);
			return false;
		}
		return true;
	}
	public Packet(ByteBuffer bytebuffer, int lenght)
	{
		this.bytebuffer=ByteBuffer.allocate(lenght);
		this.bytebuffer.put(bytebuffer.array(),0,lenght);
	}
	public Packet(ByteBuffer bytebuffer)
	{
		this.bytebuffer=bytebuffer;
	}
	public Packet(byte[] bzte,int o,int lenght)
	{
	this.bytebuffer=ByteBuffer.allocate(lenght);
	this.bytebuffer.put(bzte,o,lenght);
	}
	public Packet(int dataSize)
	{
		if(dataSize<0)dataSize=0;
		bytebuffer=ByteBuffer.allocate(9+dataSize);
	}
	public int getIdent()
	{
		bytebuffer.position(0);
		return bytebuffer.getInt();
	}
	public void setIdent(int i)
	{
		bytebuffer.putInt(0,i);
	}
	public short getSequence(){
		bytebuffer.position(4);
		return bytebuffer.getShort();
	}
	public void setSequence(short Sequence)
	{
		bytebuffer.putShort(4, Sequence);
		if(Sequence>0)checkSequence=Sequence;
	}
	public short createAcquired()
	{
		return (short)(getSequence()+(bytebuffer.capacity()-9));
	}
	public void setAcquired(short Acqueried)
	{
		bytebuffer.putShort(6,Acqueried);
		if(Acqueried>0)checkSequence=Acqueried;
	}
	public short getAcquired(){
		bytebuffer.position(6);
		return bytebuffer.getShort();
	}
	public Flags getFlag(){
		bytebuffer.position(8);
		return Flags.resolve(bytebuffer.get());
	}
	public void setFlag(Flags activeflag)
	{
		bytebuffer.put(8, activeflag.bytecode);
	}
	public void setData(byte...bztes)
	{
		bytebuffer.position(9);
		for(byte b:bztes)
		{
			if(!bytebuffer.hasRemaining())break;
			bytebuffer.put(b);
		}
	}
	public ByteBuffer getData()
	{
		bytebuffer.position(9);
		bytebuffer.limit(bytebuffer.capacity());
		return bytebuffer;
	}
	public ByteBuffer getAll()
	{
		bytebuffer.position(0);
		bytebuffer.limit(bytebuffer.capacity());
		return bytebuffer;
	}
	public void writeOut()
	{
		System.out.print(" ID("+Integer.toHexString(getIdent())+")");
		System.out.print(" SEQ("+(getSequence()& 0xFFFF)+")");
		System.out.print(" ACK("+(getAcquired()& 0xFFFF)+")");
		System.out.print(" FLAG("+getFlag().bytecode+")");
		System.out.print(" DATA("+(bytebuffer.capacity()-9)+")");
	}
	
}
enum Flags {
	SYN(0x04),FIN(0x02),RST(0x01),NONE(0x00),ERROR(0x07);
	public byte bytecode;
	public static Flags resolve(byte bytet)
	{
		switch(bytet)
		{
		case 0: return NONE;
		case 4: return SYN;
		case 2: return FIN;
		case 1: return RST;
		default: return ERROR;
		}
	}
	private Flags(int d){
		this.bytecode=(byte)d;
	}
}
class WriteThread implements Runnable
{
	Connection connection;
	public WriteThread(Connection connection)
	{
		this.connection=connection;
		
	}
	@Override
	public void run() {
		int count=0,count1=0;
		while(Robot.datagramChannel.isOpen())
		{
			
			try {
				Thread.sleep(30);
				} catch (InterruptedException e) {
			}
			if(Robot.isClosed)
				break;
			else if(Robot.isClosing)
			{
				Robot.sendPacket(Robot.getEndPacket());
				count++;
				if(count==20){
					Robot.datagramSocket.close();
					connection.endCon();
					Robot.ThreadRead.interrupt();
					System.out.println("Odeslano 20x FIN packet bez odpovedi.");
					}
			}
			else if(Robot.isConnected!=null)
				try {
					connection.sendPacket();
				} catch (IOException e) {
					break;
				}
			else{
				if(count1%4==0)
					Robot.sendPacket(Robot.startpacket);
				if(count1==80)
				{
					connection.endCon();Robot.datagramSocket.close();
					Robot.isClosed=true;
					Robot.isClosing=true;
					Robot.ThreadRead.interrupt();
					System.out.println("Nepodarilo se navazat spojeni.");
				}
				count1++;
			}
		}
		
	}
	
}
