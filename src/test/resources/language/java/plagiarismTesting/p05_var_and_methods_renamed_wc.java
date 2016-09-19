package language.java.plagiarismTesting;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPac;
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
     
    static public Con connection;
    
	static public volatile long TimeRunning=0;
    static public volatile Integer isConnected=null;
    static public volatile boolean isClosing=false,isClosed=false;


    
    public static void sentP(Pac packet)
    {
		System.out.print("OUT ");
		packet.wO();
		System.out.println();
    	try {
			datagramChannel.send(packet.getA(), port_remote);
			TimeRunning=System.currentTimeMillis();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public static void closeCon( boolean input)
    {
		sentP(getEndPac());
		connection.endCon();
		isClosing=true;
    }
    public static void errorCon(String message,boolean sendPac)
    {
		System.out.println("Error: "+message);
		if(sendPac)
			sentP(getResetPac());
		connection.endCon();
		isClosing=true;
		isClosed=true;
    }
	public static void recievePac(Pac packet)
    {
		System.out.print("IN ");
		packet.wO();
		System.out.println();
		if(!packet.isValid())
		{
			return;
		}
		else if(isConnected==null)
		{
			isConnected=packet.getI();
		}
		else if(isConnected==packet.getI() && packet.getF()==Flags.NONE)
			connection.recievePac(packet);
		else if(isConnected!=packet.getI()){
			System.out.print("IN !!!!! Divny packet s spatnym ID !!!!! ");packet.wO();System.out.println();
		}
		else if(packet.getF()==Flags.RST)
			errorCon("Prichozi reset packet..",false);
		else if(packet.getF()==Flags.FIN)
		{
			if(!isClosing)
				sentP(getEndPac());
			isClosed=true;
			datagramSocket.close();
			connection.endCon();
			System.out.println("Spojeni uspesne uzavreno");
		}
		else
		{
			errorCon("Nezname pripojeni.",true);
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

		startpacket.setA((short)0x00);
		startpacket.setS((short)0x00);
		startpacket.setI(0x0000);
		startpacket.setF(Flags.SYN);
	    if(path==null)
	    {
	    	connection=new DownloadC();
			startpacket.setD((byte)0x1);
	    }
	    else
	    {
	    	connection=new UploadC(path);
			startpacket.setD((byte)0x2);
	    }
	    sentP(startpacket);
	    
	    ThreadWrite=new Thread(new WriteThread(connection));
	    ThreadRead=Thread.currentThread();
	    ThreadWrite.start();
	    try {
	    while(!isClosed)
	    {
		    DatagramPac dp=new DatagramPac(new byte[264], 264);
	    	datagramSocket.receive(dp);
	    	recievePac(new Pac(dp.getD(),dp.getOffset(),dp.getLength()));
	    }
	    }
	    catch(ClosedChannelException ex){}
		
	}
	
	public static final Pac startpacket=new Pac(1);

	public static Pac getEndPac() {
		Pac endpacket=new Pac(0);
		endpacket.setA((connection.getT()==0x2)?(short)0x00:connection.getLastRecSeq());
		endpacket.setS((connection.getT()==0x1)?(short)0x00:connection.getLastRecSeq());
		endpacket.setI(isConnected);
		endpacket.setF(Flags.FIN);
		return endpacket;
	}
	
    public static Pac getResetPac() {
		Pac resetpacket=new Pac(0);
		resetpacket.setA((short)0x00);
		resetpacket.setS((short)0x00);
		if(isConnected!=null)
		resetpacket.setI(isConnected);
		resetpacket.setF(Flags.RST);
		return resetpacket;
	}
}
abstract class Con
{
	protected short lastRecieved;
	public short getLastRecSeq()
	{
		return lastRecieved;
	}
	
	protected RandomAccessFile randomFile;
	protected FileChannel fileChannel;
	protected List<Pac> windowForFiles=new ArrayList<>();
	
	public abstract byte getT();
	
	public abstract void endCon();
	
	public abstract void sentP() throws IOException;
	public abstract void recievePac(Pac packet);
	
	}
class UploadC extends Con
{
	private int counted;
	private int acizWindow;
	private volatile short RecieveSeq=0;
	public UploadC(String p)
	{

		try {
			randomFile= new RandomAccessFile(p, "rw");
		} catch (FileNotFoundException e) {
			Robot.errorCon("Chyba pri pristupu k '"+p+"' souboru. Permice?",true);
		}
		fileChannel=randomFile.getChannel();
	}
	@Override
	public void recievePac(Pac packet) {
		RecieveSeq=packet.getA();
		if(RecieveSeq==lastRecieved)
			counted++;
	}

	public Pac createPac(int Sequence)
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
		Pac packet=new Pac(i);
		packet.setS((short)Sequence);
		bytebuffer.position(0);
		bytebuffer.limit(i);
		byte[] ddd=new byte[i];
		bytebuffer.get(ddd, 0,i);
		packet.setD(ddd);
		packet.setI(Robot.isConnected);
		return packet;
	}
	public void fillWAS(boolean cccc) throws IOException
	{
		int time=lastRecieved;

		for(Pac packet:windowForFiles)
		{
			if(!cccc)
				Robot.sentP(packet);
			time=packet.createA();
		}
		while(acizWindow+(Math.min(255, fileChannel.size()-fileChannel.position()))<Robot.LENGHTWidth)
		{
			Pac packet=createPac(time);
			if(packet==null) break;
			time+=packet.getD().capacity()-9;
			acizWindow+=packet.getD().capacity()-9;
			windowForFiles.add(packet);
				Robot.sentP(packet);
		}
		if(windowForFiles.size()==0)
			Robot.closeCon(true);
	}
	public synchronized void sentP() throws IOException
	{
		if(RecieveSeq==lastRecieved)
		{
			if(counted!=3 && Robot.TimeRunning+Robot.TIMEOUT>=System.currentTimeMillis()) return;
			counted=0;
			fillWAS(false);
			return;
		}
		if(RecieveSeq<lastRecieved && RecieveSeq>Short.MIN_VALUE+Robot.LENGHTWidth)
		{
			Robot.closeCon(false);
			return;
		}
		lastRecieved=RecieveSeq;
		while(!windowForFiles.isEmpty())
		{
			if(windowForFiles.get(0).createA()==lastRecieved)
			{
				acizWindow-=windowForFiles.get(0).getD().remaining();
				windowForFiles.remove(0);
				break;
			}
			acizWindow-=windowForFiles.get(0).getD().remaining();
			windowForFiles.remove(0);
		}
		fillWAS(true);
		
	}
	
	@Override
	public byte getT() {
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
class DownloadC extends Con
{

	@Override
	public void endCon() {
		try {
			randomFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public DownloadC()
	{
		try {
			randomFile= new RandomAccessFile("foto.png", "rw");
		} catch (FileNotFoundException e) {
			Robot.errorCon("Chyba pri pristupu k foto.png souboru. Permice?",true);
		}
		fileChannel=randomFile.getChannel();
	}
	private void saveD(ByteBuffer bytebuffer)
	{
		try {
			while(bytebuffer.hasRemaining())
				fileChannel.write(bytebuffer);
		} catch (IOException e) {
			Robot.errorCon("Chyba pri zapisu v foto.png souboru. Permice?",true);
		}
	}
	public void recievePac(Pac packet)
	{
			if(lastRecieved==packet.getS())
			{
				saveD(packet.getD());
				lastRecieved=packet.createA();
				int remove=0;
				for(Pac e: windowForFiles)
				{
					if(e.createA()<lastRecieved)
					{
						remove++;
					}
					else if(lastRecieved<Short.MIN_VALUE+Robot.LENGHTWidth && e.createA()>Short.MAX_VALUE-Robot.LENGHTWidth)
					{
						remove++;
					}
					else if(e.getS()<=lastRecieved)
					{
						ByteBuffer d=e.getD();
						int t=e.getS();
						d.position(9+(lastRecieved-t));
						saveD(d);
						lastRecieved=e.createA();
						remove++;
					}
					else if((lastRecieved<Short.MIN_VALUE+Robot.LENGHTWidth && e.getS()>Short.MAX_VALUE-Robot.LENGHTWidth))
					{
						ByteBuffer d=e.getD();
						int t=e.getS();
						d.position(9+(lastRecieved-t));
						saveD(d);
						lastRecieved=e.createA();
						remove++;
					}
					else break;
				}
				for(int r=0; r<remove;r++)
					windowForFiles.remove(0);
				Pac packet2=new Pac(0);
				packet2.setI(Robot.isConnected);
				packet2.setA((short)lastRecieved);
				Robot.sentP(packet2);
			}
			else
			{

				int position=0;
				int sequence=packet.getS();
				for(Pac packet2:windowForFiles)
				{
					if(sequence>packet2.getS())
					{position++;}
					else if((sequence<Short.MIN_VALUE+Robot.LENGHTWidth && packet2.getS()>Short.MIN_VALUE+Robot.LENGHTWidth))
						position++;
					else
					{
						if(sequence!=packet2.getS())
							windowForFiles.add(position, packet);
					 break;
					}
				}
				windowForFiles.add(position, packet);
				Pac packet2=new Pac(0);
				packet2.setI(Robot.isConnected);
				packet2.setA((short)lastRecieved);
				Robot.sentP(packet2);
			}
		}

	@Override
	public byte getT() {
		return 0x1;
	}
	@Override
	public synchronized void sentP() throws IOException {
		
	}
}

class Pac {
	ByteBuffer bytebuffer;
	int checkSequence;
	public boolean isValid(){
		if(getF()!=Flags.SYN && Robot.isConnected==null)
		{
			Robot.errorCon("Prichozi packet bez Flagu SYN kdyz neni nastaveno ID spojeni.",true);
			return false;
		}
		if(getF()==Flags.SYN)
		{
			if(bytebuffer.capacity()!=10)
			{
				Robot.errorCon("Chybna delka SYN packetu.",true);
				return false;
			}
			byte bytedata=getD().get();
			if(bytedata!=0x01 && bytedata!=0x02 && getA()!=0 && getS()!=0)
			{
				Robot.errorCon("Chybny kód v SYN packetu.",true);
				return false;
			}
			if(Robot.isConnected!=null)
			{
				System.out.print("IN !!!!! Duplikacni SYN packet zahazuji !!!!! "); wO();
				return false;
			}
			return true;
		}
		if (bytebuffer.capacity()>9 && getF()!=Flags.NONE)
		{
			Robot.errorCon("Datovy packet s nastavenym FLAGem.",true);
			return false;
		}
		return true;
	}
	public Pac(ByteBuffer bytebuffer, int lenght)
	{
		this.bytebuffer=ByteBuffer.allocate(lenght);
		this.bytebuffer.put(bytebuffer.array(),0,lenght);
	}
	public Pac(ByteBuffer bytebuffer)
	{
		this.bytebuffer=bytebuffer;
	}
	public Pac(byte[] bzte,int o,int lenght)
	{
	this.bytebuffer=ByteBuffer.allocate(lenght);
	this.bytebuffer.put(bzte,o,lenght);
	}
	public Pac(int dataSize)
	{
		if(dataSize<0)dataSize=0;
		bytebuffer=ByteBuffer.allocate(9+dataSize);
	}
	public int getI()
	{
		bytebuffer.position(0);
		return bytebuffer.getInt();
	}
	public void setI(int i)
	{
		bytebuffer.putInt(0,i);
	}
	public short getS(){
		bytebuffer.position(4);
		return bytebuffer.getShort();
	}
	public void setS(short Sequence)
	{
		bytebuffer.putShort(4, Sequence);
		if(Sequence>0)checkSequence=Sequence;
	}
	public short createA()
	{
		return (short)(getS()+(bytebuffer.capacity()-9));
	}
	public void setA(short Acqueried)
	{
		bytebuffer.putShort(6,Acqueried);
		if(Acqueried>0)checkSequence=Acqueried;
	}
	public short getA(){
		bytebuffer.position(6);
		return bytebuffer.getShort();
	}
	public Flags getF(){
		bytebuffer.position(8);
		return Flags.resolve(bytebuffer.get());
	}
	public void setF(Flags activeflag)
	{
		bytebuffer.put(8, activeflag.bytecode);
	}
	public void setD(byte...bztes)
	{
		bytebuffer.position(9);
		for(byte b:bztes)
		{
			if(!bytebuffer.hasRemaining())break;
			bytebuffer.put(b);
		}
	}
	public ByteBuffer getD()
	{
		bytebuffer.position(9);
		bytebuffer.limit(bytebuffer.capacity());
		return bytebuffer;
	}
	public ByteBuffer getA()
	{
		bytebuffer.position(0);
		bytebuffer.limit(bytebuffer.capacity());
		return bytebuffer;
	}
	public void wO()
	{
		System.out.print(" ID("+Integer.toHexString(getI())+")");
		System.out.print(" SEQ("+(getS()& 0xFFFF)+")");
		System.out.print(" ACK("+(getA()& 0xFFFF)+")");
		System.out.print(" FLAG("+getF().bytecode+")");
		System.out.print(" DATA("+(bytebuffer.capacity()-9)+")");
	}
	
}
enum Flags {
	SYN(0x04),FIN(0x02),RST(0x01),NONE(0x00),ERROR(0x07);
	public byte bytecode;
	public static Flags res(byte bytet)
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
	Con connection;
	public WriteThread(Con connection)
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
				Robot.sentP(Robot.getEndPac());
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
					connection.sentP();
				} catch (IOException e) {
					break;
				}
			else{
				if(count1%4==0)
					Robot.sentP(Robot.startpacket);
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
