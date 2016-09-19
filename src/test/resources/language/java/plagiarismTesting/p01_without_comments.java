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
	public static final short WINDOW_LENGHT= 2_040;	
	public static final int T_OUT=100;

	static DatagramChannel channel;
	static public Thread readThread,writeThread;
    static DatagramSocket socket;
    static  SocketAddress localport,remote;
     
    static public Connection con;
    
	static public volatile long T=0;
    static public volatile Integer connection=null;
    static public volatile boolean closing=false,closed=false;


    
    public static void sendPacket(Packet pckt)
    {
		System.out.print("OUT ");
		pckt.writeOut();
		System.out.println();
    	try {
			channel.send(pckt.getAll(), remote);
			T=System.currentTimeMillis();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public static void closeConnection( boolean type)
    {
		sendPacket(getEndPacket());
		con.endCon();
		closing=true;
    }
    public static void errorConnection(String msg,boolean sendPckt)
    {
		System.out.println("Error: "+msg);
		if(sendPckt)
			sendPacket(getResetPacket());
		con.endCon();
		closing=true;
		closed=true;
    }
	public static void recievePacket(Packet pckt)
    {
		System.out.print("IN ");
		pckt.writeOut();
		System.out.println();
		if(!pckt.isValid())
		{
			return;
		}
		else if(connection==null)
		{
			connection=pckt.getIdent();
		}
		else if(connection==pckt.getIdent() && pckt.getFlag()==Flags.NONE)
			con.recievePacket(pckt);
		else if(connection!=pckt.getIdent()){
			System.out.print("IN !!!!! Divny packet s spatnym ID !!!!! ");pckt.writeOut();System.out.println();
		}
		else if(pckt.getFlag()==Flags.RST)
			errorConnection("Prichozi reset packet..",false);
		else if(pckt.getFlag()==Flags.FIN)
		{
			if(!closing)
				sendPacket(getEndPacket());
			closed=true;
			socket.close();
			con.endCon();
			System.out.println("Spojeni uspesne uzavreno");
		}
		else
		{
			errorConnection("Nezname pripojeni.",true);
		}
    }
    
	public static void main(String... args) throws IOException
	{
		String addr;
		int port=4000;
		String firmwarepath = null;
		if(args.length>2)
		{
			System.out.println("Wrong args");
			return;
		}
		if(args.length==2)
			firmwarepath=args[1];
		addr=args[0];
		if(addr.contains(":"))
		{
			try {
			port=Integer.parseInt(addr.substring(addr.indexOf(":")+1));
			} catch (NumberFormatException e)
			{
				System.out.println("Not a port");
				return;
			}
			addr=addr.substring(0,addr.indexOf(":"));
		}
	    localport = new InetSocketAddress(0); 

		channel = DatagramChannel.open();
	    socket=channel.socket();
	    socket.setSoTimeout(90000); 
	    socket.bind(localport);
	    remote=new InetSocketAddress(addr,port);
	    channel.connect(remote);

		startpckt.setAcquired((short)0x00);
		startpckt.setSequence((short)0x00);
		startpckt.setIdent(0x0000);
		startpckt.setFlag(Flags.SYN);
	    if(firmwarepath==null)
	    {
	    	con=new DownloadConnection();
			startpckt.setData((byte)0x1);
	    }
	    else
	    {
	    	con=new UploadConnection(firmwarepath);
			startpckt.setData((byte)0x2);
	    }
	    sendPacket(startpckt);
	    
	    writeThread=new Thread(new WriteThread(con));
	    readThread=Thread.currentThread();
	    writeThread.start();
	    try {
	    while(!closed)
	    {
		    DatagramPacket dp=new DatagramPacket(new byte[264], 264);
	    	socket.receive(dp);
	    	recievePacket(new Packet(dp.getData(),dp.getOffset(),dp.getLength()));
	    }
	    }
	    catch(ClosedChannelException ex){}
		
	}
	
	public static final Packet startpckt=new Packet(1);

	public static Packet getEndPacket() {
		Packet endpckt=new Packet(0);
		endpckt.setAcquired((con.getType()==0x2)?(short)0x00:con.getLastRecSeq());
		endpckt.setSequence((con.getType()==0x1)?(short)0x00:con.getLastRecSeq());
		endpckt.setIdent(connection);
		endpckt.setFlag(Flags.FIN);
		return endpckt;
	}
	
    public static Packet getResetPacket() {
		Packet rstpckt=new Packet(0);
		rstpckt.setAcquired((short)0x00);
		rstpckt.setSequence((short)0x00);
		if(connection!=null)
		rstpckt.setIdent(connection);
		rstpckt.setFlag(Flags.RST);
		return rstpckt;
	}
}
abstract class Connection
{
	protected short lastRecieveSequence;
	public short getLastRecSeq()
	{
		return lastRecieveSequence;
	}
	
	protected RandomAccessFile aFile;
	protected FileChannel file;
	protected List<Packet> window=new ArrayList<>();
	
	public abstract byte getType();
	
	public abstract void endCon();
	
	public abstract void sendPacket() throws IOException;
	public abstract void recievePacket(Packet pckt);
	
	}
class UploadConnection extends Connection
{
	private int c;
	private int acWindow;
	private volatile short RecieveSequence=0;
	public UploadConnection(String path)
	{

		try {
			aFile= new RandomAccessFile(path, "rw");
		} catch (FileNotFoundException e) {
			Robot.errorConnection("Chyba pri pristupu k '"+path+"' souboru. Permice?",true);
		}
		file=aFile.getChannel();
	}
	@Override
	public void recievePacket(Packet pckt) {
		RecieveSequence=pckt.getAcquired();
		if(RecieveSequence==lastRecieveSequence)
			c++;
	}

	public Packet createPacket(int Seq)
	{
		ByteBuffer bb=ByteBuffer.allocate(255);
		int i=0;
		try {
			i = file.read(bb);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(i==-1)
		{
			return null;
		}
		Packet pckt=new Packet(i);
		pckt.setSequence((short)Seq);
		bb.position(0);
		bb.limit(i);
		byte[] d=new byte[i];
		bb.get(d, 0,i);
		pckt.setData(d);
		pckt.setIdent(Robot.connection);
		return pckt;
	}
	public void fillWindowAndSend(boolean c) throws IOException
	{
		int t=lastRecieveSequence;

		for(Packet pckt:window)
		{
			if(!c)
				Robot.sendPacket(pckt);
			t=pckt.createAcquired();
		}
		while(acWindow+(Math.min(255, file.size()-file.position()))<Robot.WINDOW_LENGHT)
		{
			Packet pckt=createPacket(t);
			if(pckt==null) break;
			t+=pckt.getData().capacity()-9;
			acWindow+=pckt.getData().capacity()-9;
			window.add(pckt);
				Robot.sendPacket(pckt);
		}
		if(window.size()==0)
			Robot.closeConnection(true);
	}
	public synchronized void sendPacket() throws IOException
	{
		if(RecieveSequence==lastRecieveSequence)
		{
			if(c!=3 && Robot.T+Robot.T_OUT>=System.currentTimeMillis()) return;
			c=0;
			fillWindowAndSend(false);
			return;
		}
		if(RecieveSequence<lastRecieveSequence && RecieveSequence>Short.MIN_VALUE+Robot.WINDOW_LENGHT)
		{
			Robot.closeConnection(false);
			return;
		}
		lastRecieveSequence=RecieveSequence;
		while(!window.isEmpty())
		{
			if(window.get(0).createAcquired()==lastRecieveSequence)
			{
				acWindow-=window.get(0).getData().remaining();
				window.remove(0);
				break;
			}
			acWindow-=window.get(0).getData().remaining();
			window.remove(0);
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
			aFile.close();
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
			aFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public DownloadConnection()
	{
		try {
			aFile= new RandomAccessFile("foto.png", "rw");
		} catch (FileNotFoundException e) {
			Robot.errorConnection("Chyba pri pristupu k foto.png souboru. Permice?",true);
		}
		file=aFile.getChannel();
	}
	private void saveData(ByteBuffer b)
	{
		try {
			while(b.hasRemaining())
				file.write(b);
		} catch (IOException e) {
			Robot.errorConnection("Chyba pri zapisu v foto.png souboru. Permice?",true);
		}
	}
	public void recievePacket(Packet pckt)
	{
			if(lastRecieveSequence==pckt.getSequence())
			{
				saveData(pckt.getData());
				lastRecieveSequence=pckt.createAcquired();
				int remove=0;
				for(Packet e: window)
				{
					if(e.createAcquired()<lastRecieveSequence)
					{
						remove++;
					}
					else if(lastRecieveSequence<Short.MIN_VALUE+Robot.WINDOW_LENGHT && e.createAcquired()>Short.MAX_VALUE-Robot.WINDOW_LENGHT)
					{
						remove++;
					}
					else if(e.getSequence()<=lastRecieveSequence)
					{
						ByteBuffer d=e.getData();
						int t=e.getSequence();
						d.position(9+(lastRecieveSequence-t));
						saveData(d);
						lastRecieveSequence=e.createAcquired();
						remove++;
					}
					else if((lastRecieveSequence<Short.MIN_VALUE+Robot.WINDOW_LENGHT && e.getSequence()>Short.MAX_VALUE-Robot.WINDOW_LENGHT))
					{
						ByteBuffer d=e.getData();
						int t=e.getSequence();
						d.position(9+(lastRecieveSequence-t));
						saveData(d);
						lastRecieveSequence=e.createAcquired();
						remove++;
					}
					else break;
				}
				for(int r=0; r<remove;r++)
					window.remove(0);
				Packet p=new Packet(0);
				p.setIdent(Robot.connection);
				p.setAcquired((short)lastRecieveSequence);
				Robot.sendPacket(p);
			}
			else
			{

				int pos=0;
				int sq=pckt.getSequence();
				for(Packet p:window)
				{
					if(sq>p.getSequence())
					{pos++;}
					else if((sq<Short.MIN_VALUE+Robot.WINDOW_LENGHT && p.getSequence()>Short.MIN_VALUE+Robot.WINDOW_LENGHT))
						pos++;
					else
					{
						if(sq!=p.getSequence())
							window.add(pos, pckt);
					 break;
					}
				}
				window.add(pos, pckt);
				Packet p=new Packet(0);
				p.setIdent(Robot.connection);
				p.setAcquired((short)lastRecieveSequence);
				Robot.sendPacket(p);
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
	ByteBuffer data;
	int checkSeq;
	public boolean isValid(){
		if(getFlag()!=Flags.SYN && Robot.connection==null)
		{
			Robot.errorConnection("Prichozi packet bez Flagu SYN kdyz neni nastaveno ID spojeni.",true);
			return false;
		}
		if(getFlag()==Flags.SYN)
		{
			if(data.capacity()!=10)
			{
				Robot.errorConnection("Chybna delka SYN packetu.",true);
				return false;
			}
			byte b=getData().get();
			if(b!=0x01 && b!=0x02 && getAcquired()!=0 && getSequence()!=0)
			{
				Robot.errorConnection("Chybny kód v SYN packetu.",true);
				return false;
			}
			if(Robot.connection!=null)
			{
				System.out.print("IN !!!!! Duplikacni SYN packet zahazuji !!!!! "); writeOut();
				return false;
			}
			return true;
		}
		if (data.capacity()>9 && getFlag()!=Flags.NONE)
		{
			Robot.errorConnection("Datovy packet s nastavenym FLAGem.",true);
			return false;
		}
		return true;
	}
	public Packet(ByteBuffer data, int size)
	{
		this.data=ByteBuffer.allocate(size);
		this.data.put(data.array(),0,size);
	}
	public Packet(ByteBuffer data)
	{
		this.data=data;
	}
	public Packet(byte[] data,int off,int lenght)
	{
	this.data=ByteBuffer.allocate(lenght);
	this.data.put(data,off,lenght);
	}
	public Packet(int dataLenght)
	{
		if(dataLenght<0)dataLenght=0;
		data=ByteBuffer.allocate(9+dataLenght);
	}
	public int getIdent()
	{
		data.position(0);
		return data.getInt();
	}
	public void setIdent(int Ident)
	{
		data.putInt(0,Ident);
	}
	public short getSequence(){
		data.position(4);
		return data.getShort();
	}
	public void setSequence(short Seq)
	{
		data.putShort(4, Seq);
		if(Seq>0)checkSeq=Seq;
	}
	public short createAcquired()
	{
		return (short)(getSequence()+(data.capacity()-9));
	}
	public void setAcquired(short Acq)
	{
		data.putShort(6,Acq);
		if(Acq>0)checkSeq=Acq;
	}
	public short getAcquired(){
		data.position(6);
		return data.getShort();
	}
	public Flags getFlag(){
		data.position(8);
		return Flags.resolve(data.get());
	}
	public void setFlag(Flags flag)
	{
		data.put(8, flag.code);
	}
	public void setData(byte...bs)
	{
		data.position(9);
		for(byte b:bs)
		{
			if(!data.hasRemaining())break;
			data.put(b);
		}
	}
	public ByteBuffer getData()
	{
		data.position(9);
		data.limit(data.capacity());
		return data;
	}
	public ByteBuffer getAll()
	{
		data.position(0);
		data.limit(data.capacity());
		return data;
	}
	public void writeOut()
	{
		System.out.print(" ID("+Integer.toHexString(getIdent())+")");
		System.out.print(" SEQ("+(getSequence()& 0xFFFF)+")");
		System.out.print(" ACK("+(getAcquired()& 0xFFFF)+")");
		System.out.print(" FLAG("+getFlag().code+")");
		System.out.print(" DATA("+(data.capacity()-9)+")");
	}
	
}
enum Flags {
	SYN(0x04),FIN(0x02),RST(0x01),NONE(0x00),ERROR(0x07);
	public byte code;
	public static Flags resolve(byte b)
	{
		switch(b)
		{
		case 0: return NONE;
		case 4: return SYN;
		case 2: return FIN;
		case 1: return RST;
		default: return ERROR;
		}
	}
	private Flags(int data){
		this.code=(byte)data;
	}
}
class WriteThread implements Runnable
{
	Connection con;
	public WriteThread(Connection con)
	{
		this.con=con;
		
	}
	@Override
	public void run() {
		int c=0,d=0;
		while(Robot.channel.isOpen())
		{
			
			try {
				Thread.sleep(30);
				} catch (InterruptedException e) {
			}
			if(Robot.closed)
				break;
			else if(Robot.closing)
			{
				Robot.sendPacket(Robot.getEndPacket());
				c++;
				if(c==20){
					Robot.socket.close();
					con.endCon();
					Robot.readThread.interrupt();
					System.out.println("Odeslano 20x FIN packet bez odpovedi.");
					}
			}
			else if(Robot.connection!=null)
				try {
					con.sendPacket();
				} catch (IOException e) {
					break;
				}
			else{
				if(d%4==0)
					Robot.sendPacket(Robot.startpckt);
				if(d==80)
				{
					con.endCon();Robot.socket.close();
					Robot.closed=true;
					Robot.closing=true;
					Robot.readThread.interrupt();
					System.out.println("Nepodarilo se navazat spojeni.");
				}
				d++;
			}
		}
		
	}
	
}
