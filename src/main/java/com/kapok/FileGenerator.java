package com.kapok;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.concurrent.Callable;

@Command(name = "file-generator", mixinStandardHelpOptions = true, version = "file-generator 1.0",
    description = "Generate file according to the given size. ")
public class FileGenerator implements Callable<Integer> {

  @Option(names = {"-p", "--path"}, description = "The path of the file to be generated. ")
  private String filePath = "output.dat";

  @Option(names = {"-s", "--size"}, description = "The size of the file to be generated. ")
  private String size = "63MB";

  @Override
  public Integer call() throws Exception {
    DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(filePath));
    long bytesToWrite = FormatUtil.parseSpaceSize(size);
    long bytesToWriteLeft = bytesToWrite;
    while (bytesToWriteLeft > 0) {
      int bytesSize = (int) Math.min(bytesToWriteLeft, 1024);
      byte[] bytes = new byte[bytesSize];
      outputStream.write(bytes);
      bytesToWriteLeft -= bytesSize;
    }
    return 0;
  }

  public static void main(String... args) {
    int exitCode = new CommandLine(new FileGenerator()).execute(args);
    System.exit(exitCode);
  }
}