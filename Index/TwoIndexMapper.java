package com.sysadmin.mapreduce.index;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TwoIndexMapper extends Mapper<LongWritable, Text, Text, Text> {

	Text k = new Text();
	Text v = new Text();

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		// ��ȡһ������
		String line = value.toString();

		// ��ȡ����
		String[] split = line.split("--");

		// Ϊkey��value��ֵ
		k.set(split[0]);
		v.set(split[1]);

		// д��
		context.write(k, v);
	}
}
