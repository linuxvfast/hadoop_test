package com.sysadmin.mapreduce.weblogcleaning;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WeblogMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		// 从文件获取一行数据
		String line = value.toString();

		// 解析日志的方法
		boolean result = parseLog(line, context);

		// 判断数据是否符合需求
		if (!result) {
			return;
		}

		// 如果不符合需求直接返回（丢弃本条日志）

		// 把符合需求的日志写出去
		context.write(value, NullWritable.get());

	}

	private boolean parseLog(String line, Context context) {
		// 截取日志
		String[] fields = line.split(" ");

		// 判断字段长度是否大于11
		if (fields.length > 11) {
			// 记录符合需求的次数
			context.getCounter("map", "true").increment(1);
			return true;
		} else {
			// 记录不符合需求的次数
			context.getCounter("map", "false").increment(1);
			return false;
		}
	}
}
