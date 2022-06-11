package tech.fastool.core.id.snowflake;

import tech.fastool.core.exceptions.IdGeneratorException;
import tech.fastool.core.lang.StringUtil;

/**
 * Twitter提供的雪花算法，该实现算法性能很一般
 * 后续研读考虑改造下面的https://www.cnblogs.com/xiaotian0422/p/15896510.html
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-11
 */
public class TwitterSnowflake extends FastSnowflake {

    public TwitterSnowflake() {
        super();
    }

    public TwitterSnowflake(SnowFlakeOptions options) {
        super(options);
    }

    /**
     * 生成ID
     *
     * @return id
     */
    @Override
    public long nextId() {
        synchronized (SYNC_LOCK) {
            long currentTimeTick = this.getCurrentTimeTick();

            if (this.lastTimeTick == currentTimeTick) {
                if (this.currentSeqNumber++ > this.maxSeqNumber) {
                    this.currentSeqNumber = this.minSeqNumber;
                    currentTimeTick = this.getNextTimeTick();
                }
            } else {
                this.currentSeqNumber = this.minSeqNumber;
            }

            if (currentTimeTick < this.lastTimeTick) {
                throw new IdGeneratorException(StringUtil.format("Time error for {} milliseconds", this.lastTimeTick - currentTimeTick));
            }

            this.lastTimeTick = currentTimeTick;

            return ((currentTimeTick << this.timestampShift) + ((long) this.workerId << this.seqBitLength) + (int) this.currentSeqNumber);
        }
    }
}
