package com.lt.dom.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.lt.dom.oct.RedemptionEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

public class EasyWriteDemo1 {

    private String fileName;
    public EasyWriteDemo1(String fileLocation) {
        this.fileName = fileLocation;
    }

    /**
     * 最简单的写
     * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>2. 直接写即可
     */

    public void simpleWrite() {
        // 写法1
       // String fileName = "D:\\IDEA_workspace\\easyExcel\\srceasyExcel.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());
    }

    //写入数据、这里可以通过视图层获取数据写入到excel
    //这里测试的是20w数据量
    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        long before = System.currentTimeMillis();
        for (int i = 0; i < 200000; i++) {
            DemoData data = new DemoData();
            data.setName("国服冰");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            data.setDate(new Date());
            data.setSal(10000.0);
            list.add(data);
        }
        long after = System.currentTimeMillis();
        System.out.println("用时："+(after-before)+"ms");
        return list;
    }

    public void exportVoucher(List<ExcelVoucher> collect) {
        EasyExcel.write(fileName, ExcelVoucher.class).sheet("模板").doWrite(collect);
    }

    public void exportRedemptionEntry(List<ExcelRedemption> collect) {

        EasyExcel.write(fileName, ExcelRedemption.class)
             //   .registerWriteHandler(new TitleSheetWriteHandler("我是一个小标题",2)) // 标题及样式，lastCol为标题第0列到底lastCol列的宽度
                //设置默认样式及写入头信息开始的行数
                .relativeHeadRowIndex(1)
                .sheet("模板").doWrite(collect);
    }



    public static <T> void createExcelManySheets(String filePath, java.util.Map<String, List<T>> dataMap, Class<T> clazz) throws Exception {
        ExcelWriter excelWriter = EasyExcel.write(filePath, clazz).build();
        try {
            int index = 0;
            for (Map.Entry<String, List<T>> entry : dataMap.entrySet()) {
                String sheetName = entry.getKey();
                List<T> data = entry.getValue();
                WriteSheet sheet = EasyExcel.writerSheet(index++, sheetName).build();
                excelWriter.write(data, sheet);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }
/*————————————————
    版权声明：本文为CSDN博主「布碗」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
    原文链接：https://blog.csdn.net/sinat_36553913/article/details/103941019*/
}