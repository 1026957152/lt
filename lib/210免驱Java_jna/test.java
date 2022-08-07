import com.sun.jna.*;


interface CLibrary extends Library {
		CLibrary sdtapi = (CLibrary)
		Native.loadLibrary((Platform.isWindows() ? ".\\dll\\sdtapi" : "c"),
		CLibrary.class);
		int InitComm(int port);
		int Authenticate();
		int GetSAMIDToStr(byte[] samid);
		int ReadBaseInfos(byte[] Name,byte[] Gender,byte[] Folk,byte[] BirthDay,byte[] Code,byte[] Address,byte[] Agency,byte[] ExpireStart,byte[] ExpireEnd);
		int ReadBaseInfosPhoto(byte[] Name,byte[] Gender,byte[] Folk,byte[] BirthDay,byte[] Code,byte[] Address,byte[] Agency,byte[] ExpireStart,byte[] ExpireEnd,String Dir);
		int HID_BeepLED(boolean  BeepON,boolean  LEDON,int duration);
}


class test {

    	public static void main(String[] args) {
    	
			byte sid[] = new byte[37];
			byte name[] = new byte[31];
			byte sex[] = new byte[3];
			byte folk[] = new byte[10];
			byte birth[] = new byte[9];
			byte code[] = new byte[19];
			byte add[] = new byte[71];
			byte agency[] = new byte[31];
			byte expirestart[] = new byte[9];
			byte expireend[] = new byte[9];
			int ret=0;
			
			if (CLibrary.sdtapi.InitComm(1001)==1)
			{
					System.out.println("connect OK! �밴Ctrl+C �˳�����");
					ret=CLibrary.sdtapi.GetSAMIDToStr(sid);
					if (ret!=1)
					{
								System.out.println("GetSAMIDToStr="+ret);
								return;
					}
					System.out.println(new String(sid));
					
					while (true)
					{
							ret=CLibrary.sdtapi.Authenticate();
							if (ret==1)
							{
									System.out.println("======================================");
				
									ret=CLibrary.sdtapi.ReadBaseInfosPhoto(name,sex,folk,birth,code,add,agency,expirestart,expireend,"d:\\t2\\t3\\t4");
									if (ret!=1)
									{
										System.out.println("ReadBaseInfosPhoto="+ret);
										return;
									}									
									//CLibrary.sdtapi.HID_BeepLED(true,true,50);
									//CLibrary.sdtapi.HID_BeepLED(false,false,50);
									//CLibrary.sdtapi.HID_BeepLED(true,true,50);
				
									System.out.println(new String(name));
									System.out.println(new String(sex));
									System.out.println(new String(folk));
									System.out.println(new String(birth));
									System.out.println(new String(code));
									System.out.println(new String(add));
									System.out.println(new String(agency));
									System.out.println(new String(expirestart));
									System.out.println(new String(expireend));
							}
					}
			
			}
			else
			{
					System.out.println("connect error from java!");

			}
  }
		

}
