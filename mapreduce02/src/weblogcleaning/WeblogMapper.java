package com.sysadmin.mapreduce.weblogcleaning;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WeblogMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		// ���ļ���ȡһ������
		String line = value.toString();

		// ������־�ķ���
		boolean result = parseLog(line, context);

		// �ж������Ƿ��������
		if (!result) {
			return;
		}

		// �������������ֱ�ӷ��أ�����������־��

		// �ѷ����������־д��ȥ
		context.write(value, NullWritable.get());

	}

	private boolean parseLog(String line, Context context) {
		// ��ȡ��־
		String[] fields = line.split(" ");

		// �ж��ֶγ����Ƿ����11
		if (fields.length > 11) {
			// ��¼��������Ĵ���
			context.getCounter("map", "true").increment(1);
			return true;
		} else {
			// ��¼����������Ĵ���
			context.getCounter("map", "false").increment(1);
			return false;
		}
	}
}
