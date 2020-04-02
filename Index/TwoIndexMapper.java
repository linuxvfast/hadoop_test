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

		// 获取一行数据
		String line = value.toString();

		// 截取数据
		String[] split = line.split("--");

		// 为key和value赋值
		k.set(split[0]);
		v.set(split[1]);

		// 写出
		context.write(k, v);
	}
}
