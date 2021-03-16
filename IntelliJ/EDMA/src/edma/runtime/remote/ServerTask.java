package edma.runtime.remote;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerTask implements Runnable
{
	private IServerInstance inst;
	private Socket socket;
	public ServerTask(IServerInstance inst, Socket socket)
	{
		super();
		this.inst = inst;
		this.socket = socket;
	}
	
	@Override
	public void run()
	{
		try
		{
			BufferedInputStream buf_in = new BufferedInputStream(socket.getInputStream());
	        DataInput edma_in = new DataInputStream(buf_in);
	        BufferedOutputStream buf_out = new BufferedOutputStream(socket.getOutputStream());
	        DataOutput edma_out = new DataOutputStream(buf_out);
			int methodID = edma_in.readInt();
			try
			{
				inst.call(methodID, edma_in, edma_out);
			}
			catch(IOException e)
			{
				edma_out.writeBoolean(false);
				edma_out.writeUTF(e.toString());
			}
			buf_out.close();
			buf_in.close();
			socket.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();	
		}
	}
	
	
}
