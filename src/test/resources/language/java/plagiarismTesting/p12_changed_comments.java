/*
 * Lorem ipsum dolor sit amet consectetuer dis Curabitur ac nunc Duis. Consectetuer ligula vitae cursus ridiculus Nulla Suspendisse hendrerit enim ornare accumsan. Et lacus habitasse sit auctor In augue risus porttitor dictum id. Tristique laoreet metus libero sollicitudin Sed Sed leo adipiscing Curabitur et. Elit Aliquam congue lacus Aenean velit sed augue In congue sed. Adipiscing ipsum interdum at Quisque tristique ornare Pellentesque cursus ut et. Laoreet Nulla sem pulvinar id dolor quis hendrerit dictum venenatis adipiscing. Suscipit Pellentesque Curabitur faucibus pretium volutpat nonummy.

Nulla accumsan Duis Sed mauris ut Ut ac est Lorem vitae. In mattis et Lorem nascetur ullamcorper condimentum Quisque nibh ipsum pretium. Purus eros dictumst elit magna laoreet metus congue nisl id gravida. Sed Curabitur sed Curabitur nibh pede massa lacinia condimentum purus Ut. Interdum Lorem justo a augue ac in Aenean semper risus orci. Sit semper Maecenas et ligula aliquam Curabitur tristique congue vel turpis. Wisi leo tellus porttitor Vestibulum nibh ut semper ac felis et. Semper a ac nulla id.

In at ultrices eu Curabitur est tellus tempus dapibus dolor Pellentesque. Nibh tristique tincidunt ut hendrerit id dolor In Quisque dolor mauris. Sem lorem Nullam massa ipsum tristique Curabitur et suscipit orci mi. Pretium wisi mi wisi mauris in ultrices platea elit elit tincidunt. Vivamus Quisque pretium nisl elit vitae at Sed Aenean Nulla eu. Faucibus Vestibulum at consequat urna habitasse leo elit dui tempor nibh. Eleifend lobortis auctor Aenean pretium Quisque pellentesque tempus venenatis semper Vivamus. Nulla mauris elit tristique mattis.


 * 
 * */
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

/*
 * <p>Lorem ipsum dolor sit amet consectetuer Mauris nec et Vestibulum tempor. Nec senectus sem Morbi risus elit Phasellus nec vitae et senectus. Faucibus sem mauris lorem iaculis elit nulla leo Integer at auctor. Dolor sed libero Nam lacinia quis elit Sed Nunc pretium volutpat. Maecenas Vestibulum elit ultrices nec nibh Curabitur Cum ante auctor sem. Libero in et justo Integer Fusce et habitasse et nec convallis. Ornare Cum lacinia wisi semper Duis orci vestibulum Fusce ac ligula. Sed magnis convallis at.</p>

 * */


public class Robot {
	public static final short WINDOW_LENGHT= 2_040;	
	public static final int T_OUT=100;

	static DatagramChannel channel;
	static public Thread readThread,writeThread;
    static DatagramSocket socket;
    static  SocketAddress localport,remote;
    
    //Lorem ipsum dolor sit amet consectetuer ut amet Sed nibh In. Sed laoreet neque Pellentesque at Proin cursus Nullam et Quisque tincidunt. Aliquet quis volutpat ut Integer platea ipsum Nam et tristique Pellentesque. Quis habitasse augue tincidunt leo Aenean nec lobortis hac velit ipsum. Id ac ante laoreet eu amet mauris id tempus libero at. Id Maecenas magna malesuada Mauris et at auctor Cras ipsum libero. Accumsan semper et nunc massa nulla turpis ante sapien.


    static public Connection con;
    
    //Lorem ipsum dolor sit amet consectetuer augue id interdum orci pede. Malesuada In elit fermentum tortor rhoncus Vestibulum ut tempus Nulla nec. Id Nam lacus dolor quis ipsum Sed nulla vitae ut et. Maecenas nec vel nec Ut Aenean Pellentesque est Maecenas Aliquam eu. Id Sed enim amet Sed velit Nulla ipsum Aenean Curabitur ac. Sodales nulla Proin libero habitant nulla Nullam Nulla lacus auctor Nullam. Neque tincidunt pellentesque libero vitae cursus hendrerit eget odio commodo Quisque. Ut odio lacinia In.


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
		sendPacket(getEndPacket());      //Lorem ipsum dolor sit amet consectetuer eget condimentum quis justo Vestibulum. Fermentum tortor mauris vitae sodales sed sapien Integer vel ipsum Vivamus. Feugiat Vivamus id Aenean malesuada ipsum auctor eget et nibh eros. Morbi sed nibh Sed congue Maecenas Proin semper ut fringilla leo. Wisi at magnis dolor habitant orci augue quis sollicitudin Duis lacus. Pretium quis commodo magnis interdum malesuada odio id eros pretium tempor. In non sit justo pellentesque Nam aliquet pellentesque mauris urna dapibus. Eget sed Sed.


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
		/* 
        Lorem ipsum dolor sit amet consectetuer Sed quis metus porttitor Phasellus. Eget suscipit diam gravida Vestibulum et at pede Curabitur Vivamus nisl. Tempus Sed leo ut pellentesque eget justo Morbi urna nibh felis. Proin id quis nunc convallis vel purus elit Pellentesque risus mauris. Sed Vivamus sem et laoreet consectetuer id dapibus eu et tempus. Interdum vel nibh tincidunt est tellus Quisque tellus felis justo Aenean. Sed lacinia mauris Aliquam congue eu consequat nibh felis pellentesque ridiculus. Ac sollicitudin urna quis lacinia consectetuer.


     */
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
				System.out.println("Not a port");   //Lorem ipsum dolor sit amet consectetuer Donec massa ac Integer pretium. Proin fringilla tincidunt nascetur tellus auctor consequat orci tortor tempus semper. Vel Sed wisi Praesent nec wisi semper lacus elit dui ut. Sed turpis orci Duis porttitor pede Curabitur diam ligula adipiscing Phasellus. Tortor et habitant Aenean sociis dolor risus Curabitur vel pede quis. Dui interdum pellentesque tincidunt pulvinar non In Donec at cursus Aliquam. Interdum In ligula id ut In eros quis nec tortor congue. Lorem at libero.


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

	    /* 
      
      Lorem ipsum dolor sit amet consectetuer Vestibulum quis auctor semper netus. Nibh Curabitur dui semper Nam Phasellus sem ut amet magna dignissim. Id faucibus eget justo fames egestas ut vestibulum nunc id cursus. Id ac adipiscing commodo risus eros pretium Suspendisse volutpat ac Morbi. Massa enim molestie sed cursus tincidunt sed habitasse lorem pharetra congue. Aenean pretium ligula risus Curabitur est enim adipiscing in velit hendrerit. Neque volutpat nec quis sociis libero in justo semper ullamcorper leo. Cras semper adipiscing.

 */
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
  //Lorem ipsum dolor sit amet consectetuer ac ut Pellentesque laoreet dolor. Donec nibh volutpat urna risus nulla magna justo elit Vestibulum Aenean. Ut Curabitur consectetuer Phasellus tellus Curabitur pellentesque Nam quis ipsum justo. Pellentesque vel Proin velit nec nec convallis ante faucibus Vivamus Nam. Ligula Sed risus In.


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

	/** Lorem ipsum dolor sit amet consectetuer at id justo lacus nulla. Sagittis Ut cursus a wisi id condimentum magna ut amet orci. Ante purus eleifend pellentesque congue tristique et hendrerit velit eros cursus. Arcu magnis mollis Vestibulum egestas mattis vitae felis Nam tincidunt Curabitur. Lobortis congue ut mauris vitae.

 */
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
  //Lorem ipsum dolor sit amet consectetuer pede Sed eros feugiat eu. Lacinia dui dui vitae nec Curabitur lobortis sem eget congue Suspendisse. Laoreet pretium vel elit Nam nisl volutpat mauris habitant Nulla platea. Nibh mauris pretium nibh Nullam Curabitur non convallis et metus tempus. Laoreet Morbi.


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
				int sq=pckt.getSequence();      //Lorem ipsum dolor sit amet consectetuer auctor rhoncus In dictumst dui. Laoreet tortor ac tellus id orci tempus tortor Phasellus ac metus. Ipsum tellus tempor nec quis nulla eu accumsan semper sem Vestibulum. Tristique ipsum euismod id orci justo nec mauris malesuada ac semper. Id consectetuer.


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

/** Lorem ipsum dolor sit amet consectetuer gravida pede tempor et nec. Vel platea rhoncus porttitor eget wisi quis sodales sed ac eu. Nisl orci parturient nunc pharetra condimentum pretium quis consectetuer Integer orci. Nec malesuada in interdum ac turpis massa leo eu Cras malesuada. Eget.

 */
class Packet {
	ByteBuffer data;
	int checkSeq;
	/** Lorem ipsum dolor sit amet consectetuer Sed Aliquam Ut lacus lobortis. Congue gravida ultrices Vestibulum eros turpis pellentesque a turpis condimentum Vivamus. Id interdum nibh quis ligula felis senectus porta eget id massa. Integer cursus montes fames nisl a natoque ut id Vivamus Nunc. Platea adipiscing feugiat ultrices Quisque.

*/
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
/** Lorem ipsum dolor sit amet consectetuer orci platea Nulla massa leo. Velit nibh risus id Sed ipsum turpis urna vitae dolor sed. Lobortis Nulla pellentesque adipiscing id eleifend malesuada vitae tristique tristique pede. Orci dui justo lacus ut pretium lacinia lorem sagittis Quisque amet. Sed.

*/
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
/* Lorem ipsum dolor sit amet consectetuer dui eleifend nibh metus enim. Et at nec Vestibulum semper congue Vestibulum eget velit volutpat laoreet. Nam sed tellus eu faucibus Sed interdum sodales sociis id quam. Phasellus dolor tincidunt nibh Curabitur In lobortis Nulla turpis metus penatibus. Nisl.

 */
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
