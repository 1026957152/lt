package com.lt.dom.equip;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import com.lt.dom.config.DllConfig;
import com.lt.dom.oct.Scenario;
import com.lt.dom.otcReq.ScenarioReq;
import com.lt.dom.repository.ScenarioAssignmentRepository;
import com.sun.jna.Library;
import com.sun.jna.Native;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class JavaTest 
{
    @Autowired
    private DllConfig dllConfig;

	public interface TscLibDll extends Library
	{		
        int about ();
        int openport (String pirnterName);
        int closeport ();
        int sendcommand (String printerCommand);
        int sendBinaryData (byte[] printerCommand, int CommandLength);
        int setup (String width,String height,String speed,String density,String sensor,String vertical,String offset);
        int downloadpcx (String filename,String image_name);
        int barcode (String x,String y,String type,String height,String readable,String rotation,String narrow,String wide,String code);
        int printerfont (String x,String y,String fonttype,String rotation,String xmul,String ymul,String text);
        int clearbuffer ();
        int printlabel (String set, String copy);        
        int windowsfont (int x, int y, int fontheight, int rotation, int fontstyle, int fontunderline, String szFaceName, String content);
        int windowsfontUnicode(int x, int y, int fontheight, int rotation, int fontstyle, int fontunderline, String szFaceName, byte[] content);
        int windowsfontUnicodeLengh(int x, int y, int fontheight, int rotation, int fontstyle, int fontunderline, String szFaceName, byte[] content, int length);
        byte usbportqueryprinter();
        
	}
    TscLibDll INSTANCE = null;//
    public void createScenario(ScenarioReq scenarioReq) {
        TscLibDll INSTANCE = (TscLibDll) Native.loadLibrary(dllConfig.getDllPath_printer(), TscLibDll.class);

    }

    public static void main(String[] args)
    {


        String WT1 = "TSC Printers";
        String B1 = "20080101";

    	//unicode format
    	byte[] result_unicode = new byte[1024];
    	String word_unicode = "简体中文测试123123繁體測試";
    	result_unicode = word_unicode.getBytes(StandardCharsets.UTF_16LE);

    	//utf-8 format
    	byte[] result_utf8 = new byte[1024];
    	String word_utf8 = "TEXT 40,620,\"ARIAL.TTF\",0,12,12,\"utf8 test Wörter auf Deutsch\"";
    	result_utf8 = word_utf8.getBytes(StandardCharsets.UTF_8);

        JavaTest javaTest = new JavaTest();

        //TSCLIB_DLL.about();
        byte status = javaTest.INSTANCE.usbportqueryprinter();//0 = idle, 1 = head open, 16 = pause, following <ESC>!? command of TSPL manual
        javaTest.INSTANCE.openport("TSC TE210");
        javaTest.INSTANCE.sendcommand("SIZE 100 mm, 120 mm");
        javaTest.INSTANCE.sendcommand("SPEED 4");
        javaTest.INSTANCE.sendcommand("DENSITY 12");
        javaTest.INSTANCE.sendcommand("DIRECTION 1");
        javaTest.INSTANCE.sendcommand("SET TEAR ON");
        javaTest.INSTANCE.sendcommand("CODEPAGE UTF-8");
        javaTest.INSTANCE.clearbuffer();
        javaTest.INSTANCE.downloadpcx("\\UL.PCX", "UL.PCX");
        javaTest.INSTANCE.windowsfont(40, 490, 48, 0, 0, 0, "Arial", "Windows Font Test");
        javaTest.INSTANCE.windowsfontUnicodeLengh(40, 550, 48, 0, 0, 0, "Arial", result_unicode,word_unicode.length());
        javaTest.INSTANCE.sendcommand("PUTPCX 40,40,\"UL.PCX\"");
        javaTest.INSTANCE.sendBinaryData(result_utf8, result_utf8.length);
        javaTest.INSTANCE.barcode("40", "300", "128", "80", "1", "0", "2", "2", B1);
        javaTest.INSTANCE.printerfont("40", "440", "0", "0", "15", "15", WT1);
        javaTest.INSTANCE.printlabel("1", "1");
        javaTest.INSTANCE.closeport();

    }
    

}


