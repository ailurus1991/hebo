package com.guokr.hebo.tap;
/*
 * Copyright (c) 2007-2013 Concurrent, Inc. All Rights Reserved.
 *
 * Project and contact information: http://www.cascading.org/
 *
 * This file is part of the Cascading project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.beans.ConstructorProperties;
import java.io.IOException;

import cascading.flow.FlowProcess;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tap.hadoop.Hfs;
import cascading.tap.hadoop.io.TapOutputCollector;
import cascading.tuple.Fields;
import cascading.tuple.TupleEntrySchemeCollector;

import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCollector;

public class HfsTemplateTap extends
    BaseTemplateTap<JobConf, OutputCollector> {

  private Granularity input;
  private Granularity output;

  @ConstructorProperties({ "parent", "input", "output" })
  public HfsTemplateTap(Hfs parent, Granularity input, Granularity output) {
    this(parent,input, output, OPEN_TAPS_THRESHOLD_DEFAULT);
  }

  @ConstructorProperties({ "parent", "input", "output",
      "openTapsThreshold" })
  public HfsTemplateTap(Hfs parent, Granularity input, Granularity output,
      int openTapsThreshold) {
    super(parent, input, output, openTapsThreshold);
    this.input = input;
    this.output = output;
  }

  @ConstructorProperties({ "parent", "input", "output", "sinkMode" })
  public HfsTemplateTap(Hfs parent, Granularity input, Granularity output,
      SinkMode sinkMode) {
    super(parent,  input, output, sinkMode);
    this.input = input;
    this.output = output;
  }

  @ConstructorProperties({ "parent", "input", "output",
      "sinkMode", "keepParentOnDelete" })
  public HfsTemplateTap(Hfs parent, Granularity input, Granularity output,
      SinkMode sinkMode, boolean keepParentOnDelete) {
    this(parent, input, output, sinkMode, keepParentOnDelete,
        OPEN_TAPS_THRESHOLD_DEFAULT);
  }

  @ConstructorProperties({ "parent", "input", "output",
      "sinkMode", "keepParentOnDelete", "openTapsThreshold" })
  public HfsTemplateTap(Hfs parent, Granularity input, Granularity output,
      SinkMode sinkMode, boolean keepParentOnDelete, int openTapsThreshold) {
    super(parent,input, output, sinkMode, keepParentOnDelete,
        openTapsThreshold);
    this.input = input;
    this.output = output;
  }

  @Override
  protected TupleEntrySchemeCollector createTupleEntrySchemeCollector(
      FlowProcess<JobConf> flowProcess, Tap parent, String path)
      throws IOException {
    TapOutputCollector outputCollector = new TapOutputCollector(
        flowProcess, parent, path);

    return new TupleEntrySchemeCollector<JobConf, OutputCollector>(
        flowProcess, parent, outputCollector);
  }
}
