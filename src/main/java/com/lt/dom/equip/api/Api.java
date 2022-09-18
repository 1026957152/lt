
package com.lt.dom.equip.api;
import java.io.UnsupportedEncodingException;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

public class Api {

	public interface Vdll extends Library {
		Vdll INSTANCE = (Vdll)Native.loadLibrary("vbar", Vdll.class);
		//打开设备
		public IntByReference vbar_channel_open(int type, long parm);
		//向设备发送数据
		public int vbar_channel_send(IntByReference vbar_device,byte[] buffer,int length);
		//接收设备返回数据
		public int vbar_channel_recv(IntByReference vbar_device,byte[] buffer,int length,int milliseconds);
		//关闭设备传输通道
		public int vbar_channel_close(IntByReference vbar_device);

	}
	//初始化设备变量
	IntByReference device = null;

	IntByReference result_size = new IntByReference(1024);
	IntByReference result_type = new IntByReference(1024);

	IntByReference dev_num = new IntByReference(256);

	//打开通道
	public boolean vbarOpen() {
		device = Vdll.INSTANCE.vbar_channel_open(1,1);
		if(device != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	//背光控制
	public void vbarLight(boolean lightstate) {

		byte[] buffer = new byte[64];
		int i = 0;
		if(device != null)
		{
			if (lightstate) {
				buffer[i] = 0x55;
				buffer[++i] = (byte)0xAA;
				buffer[++i] = 0x24;
				buffer[++i] = 0x01;
				buffer[++i] = 0x00;
				buffer[++i] = 0x01;
				buffer[++i] = (byte)0xDB;

			} else {
				buffer[i] = 0x55;
				buffer[++i] = (byte) 0xAA;
				buffer[++i] = 0x24;
				buffer[++i] = 0x01;
				buffer[++i] = 0x00;
				buffer[++i] = 0x00;
				buffer[++i] = (byte) 0xDA;
			}
			Vdll.INSTANCE.vbar_channel_send(device, buffer, 64);
		}

	}

	//扫码开关
	public void controlScan(boolean scanswitch) {
		if(device != null)
		{
			byte[] buffer = new byte[64];
			int i = 0;
			if (scanswitch) {
				buffer[i] = 0x55;
				buffer[++i] = (byte)0xAA;
				buffer[++i] = 0x05;
				buffer[++i] = 0x01;
				buffer[++i] = 0x00;
				buffer[++i] = 0x00;
				buffer[++i] = (byte)0xFB;
			} else {
				buffer[i] = 0x55;
				buffer[++i] = (byte) 0xAA;
				buffer[++i] = 0x05;
				buffer[++i] = 0x01;
				buffer[++i] = 0x00;
				buffer[++i] = 0x01;
				buffer[++i] = (byte) 0xFA;
			}
			Vdll.INSTANCE.vbar_channel_send(device, buffer, 64);
		}


	}
	//接收数据
	public String getResultStr(){
		String str = null;
		byte [] bufferrecv = new byte[1024];
		int recv = Vdll.INSTANCE.vbar_channel_recv(device,bufferrecv,1024,200);
		if( recv > 0)
		{
			if (bufferrecv[0] == 85 && bufferrecv[1] == -86 && bufferrecv[3] == 0)
			{

				int datalen = (bufferrecv[4] & 0xff) + ((bufferrecv[5] << 8) & 0xff);  //高位左移位8位 按协议低位在前 高位在后 扫码数据总长度
				if (datalen > 0)
				{
					byte[] receivebuffer = new byte[datalen];
					System.arraycopy(bufferrecv, 6, receivebuffer, 0, datalen);
					try {
						str = new String(receivebuffer,"UTF-8" );
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("str result:" + str);
					return str;
				}
				else
				{
					return null;
				}
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	//关闭通道
	public void vbarClose()
	{
		if (device != null) {
			Vdll.INSTANCE.vbar_channel_close(device);
			device = null;

		}

	}
}






