package cn.edu.tsinghua.tsfile.timeseries.read.query.impl;

import cn.edu.tsinghua.tsfile.timeseries.read.common.Path;
import cn.edu.tsinghua.tsfile.timeseries.read.datatype.RowRecord;
import cn.edu.tsinghua.tsfile.timeseries.read.query.QueryDataSet;
import cn.edu.tsinghua.tsfile.timeseries.read.query.timegenerator.TimestampGenerator;
import cn.edu.tsinghua.tsfile.timeseries.read.reader.impl.SeriesReaderFromSingleFileByTimestampImpl;

import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Created by zhangjinrui on 2017/12/26.
 */
public class QueryDataSetForQueryWithQueryFilterImpl implements QueryDataSet {

    private TimestampGenerator timestampGenerator;
    private LinkedHashMap<Path, SeriesReaderFromSingleFileByTimestampImpl> readersOfSelectedSeries;

    public QueryDataSetForQueryWithQueryFilterImpl(TimestampGenerator timestampGenerator, LinkedHashMap<Path, SeriesReaderFromSingleFileByTimestampImpl> readersOfSelectedSeries) {
        this.timestampGenerator = timestampGenerator;
        this.readersOfSelectedSeries = readersOfSelectedSeries;
    }

    @Override
    public boolean hasNext() throws IOException {
        return timestampGenerator.hasNext();
    }

    @Override
    public RowRecord next() throws IOException {
        long timestamp = timestampGenerator.next();
        RowRecord rowRecord = new RowRecord(timestamp);
        for (Path path : readersOfSelectedSeries.keySet()) {
            SeriesReaderFromSingleFileByTimestampImpl seriesChunkReaderByTimestamp = readersOfSelectedSeries.get(path);
            rowRecord.putField(path, seriesChunkReaderByTimestamp.getValueInTimestamp(timestamp));
        }
        return rowRecord;
    }
}
