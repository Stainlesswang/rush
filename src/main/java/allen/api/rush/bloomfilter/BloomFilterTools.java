package allen.api.rush.bloomfilter;

import orestes.bloomfilter.BloomFilter;
import orestes.bloomfilter.FilterBuilder;

/**
 * @author AllenWong
 * @date 2019/12/20 3:59 PM
 */
public class BloomFilterTools {



    public static void main(String[] args) {
        BloomFilter<String> bloomFilter=new FilterBuilder(1000,0.01)
                .name("AllenBloom")
                .redisBacked(true)
                .buildBloomFilter();
        bloomFilter.add("allen");
        System.out.println(bloomFilter.contains("allen"));
        bloomFilter.remove();
    }
}
