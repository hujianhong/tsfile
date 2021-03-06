package cn.edu.tsinghua.tsfile.timeseries.write.record.datapoint;

import cn.edu.tsinghua.tsfile.file.metadata.enums.TSDataType;
import cn.edu.tsinghua.tsfile.timeseries.write.series.IChunkWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * a subclass for Integer data type extends DataPoint
 *
 * @author kangrong
 * @see DataPoint DataPoint
 */
public class IntDataPoint extends DataPoint {
    private static final Logger LOG = LoggerFactory.getLogger(IntDataPoint.class);
    /** actual value **/
    private int value;

    /**
     * constructor of IntDataPoint, the value type will be set automatically
     * @param measurementId measurement Id
     * @param v value
     */
    public IntDataPoint(String measurementId, int v) {
        super(TSDataType.INT32, measurementId);
        this.value = v;
    }

    @Override
    public void writeTo(long time, IChunkWriter writer) throws IOException {
        if (writer == null) {
            LOG.warn("given IChunkWriter is null, do nothing and return");
            return;
        }
        writer.write(time, value);

    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setInteger(int value) {
        this.value = value;
    }
}
