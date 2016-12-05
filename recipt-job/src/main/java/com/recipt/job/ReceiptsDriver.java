package com.recipt.job;


import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;


public class ReceiptsDriver extends Configured implements Tool {

   public int run(String[] args) throws Exception {
      // check the CLI
      if (args.length != 2) {
         System.err.printf("usage: %s [generic options] <inputfile> <outputdir>\n", getClass().getSimpleName());
         System.exit(1);
      }
      // TODO change name below
      Job job = new Job(getConf(), "my receipts");

      job.setJarByClass(ReceiptsDriver.class);
      job.setMapperClass(ReceiptsMapper.class);

      // TODO comment out the Reducer class definition
      job.setReducerClass(ReceiptsReducer.class);

      job.setInputFormatClass(TextInputFormat.class);
      job.setOutputKeyClass(Text.class);
      job.setOutputValueClass(Text.class);

      // setup input and output paths
      FileInputFormat.addInputPath(job, new Path(args[0]));
      FileOutputFormat.setOutputPath(job, new Path(args[1])); 

      // launch job syncronously
      return  job.waitForCompletion(true) ? 0 : 1;


   }

   public static void main(String[] args) throws Exception { 
      Configuration conf = new Configuration();
      System.exit(ToolRunner.run(conf, new ReceiptsDriver(), args));
   } 
}
