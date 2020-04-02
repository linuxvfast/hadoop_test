package com.sysadmin.mapreduce.index;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TwoIndexReducer extends Reducer<Text, Text, Text, Text> {

	Text v = new Text();

	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

		StringBuilder StringBuilder = new StringBuilder();

		for (Text text : values) {

			StringBuilder.append(text.toString().replace("\t", "--") + "\t");
		}

		v.set(StringBuilder.toString());

		context.write(key, v);

	}
}
