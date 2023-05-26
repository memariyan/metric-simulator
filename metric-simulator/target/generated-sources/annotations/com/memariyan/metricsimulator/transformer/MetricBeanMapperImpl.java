package com.memariyan.metricsimulator.transformer;

import com.memariyan.metricsimulator.model.Metric;
import com.memariyan.metricsimulator.model.MetricType;
import com.memariyan.metricsimulator.model.Tag;
import com.memariyan.metricsimulator.spec.dto.MetricDto;
import com.memariyan.metricsimulator.spec.dto.MetricTag;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-26T11:53:55+0330",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.7 (Private Build)"
)
@Component
public class MetricBeanMapperImpl implements MetricBeanMapper {

    @Override
    public MetricType toMetricType(com.memariyan.metricsimulator.spec.dto.MetricType type) {
        if ( type == null ) {
            return null;
        }

        MetricType metricType;

        switch ( type ) {
            case COUNTER: metricType = MetricType.COUNTER;
            break;
            case SUMMARY: metricType = MetricType.SUMMARY;
            break;
            case TIMER: metricType = MetricType.TIMER;
            break;
            case GAUGE: metricType = MetricType.GAUGE;
            break;
            default: metricType = null;
        }

        return metricType;
    }

    @Override
    public Metric toMetric(MetricDto metric, String groupName) {
        if ( metric == null && groupName == null ) {
            return null;
        }

        Metric metric1 = new Metric();

        if ( metric != null ) {
            metric1.setType( toMetricType( metric.getType() ) );
            metric1.setName( metric.getName() );
            metric1.setTags( metricTagListToTagList( metric.getTags() ) );
            metric1.setValue( metric.getValue() );
            metric1.setPauseDuration( metric.getPauseDuration() );
        }
        metric1.setGroupName( groupName );

        return metric1;
    }

    @Override
    public MetricDto toMetricDto(Metric metric) {
        if ( metric == null ) {
            return null;
        }

        MetricDto metricDto = new MetricDto();

        metricDto.setType( metricTypeToMetricType( metric.getType() ) );
        metricDto.setName( metric.getName() );
        metricDto.setTags( tagListToMetricTagList( metric.getTags() ) );
        metricDto.setValue( metric.getValue() );
        metricDto.setPauseDuration( metric.getPauseDuration() );

        return metricDto;
    }

    @Override
    public List<MetricDto> toMetricDtoList(List<Metric> metrics) {
        if ( metrics == null ) {
            return null;
        }

        List<MetricDto> list = new ArrayList<MetricDto>( metrics.size() );
        for ( Metric metric : metrics ) {
            list.add( toMetricDto( metric ) );
        }

        return list;
    }

    protected Tag metricTagToTag(MetricTag metricTag) {
        if ( metricTag == null ) {
            return null;
        }

        Tag tag = new Tag();

        tag.setName( metricTag.getName() );
        tag.setValue( metricTag.getValue() );

        return tag;
    }

    protected List<Tag> metricTagListToTagList(List<MetricTag> list) {
        if ( list == null ) {
            return null;
        }

        List<Tag> list1 = new ArrayList<Tag>( list.size() );
        for ( MetricTag metricTag : list ) {
            list1.add( metricTagToTag( metricTag ) );
        }

        return list1;
    }

    protected com.memariyan.metricsimulator.spec.dto.MetricType metricTypeToMetricType(MetricType metricType) {
        if ( metricType == null ) {
            return null;
        }

        com.memariyan.metricsimulator.spec.dto.MetricType metricType1;

        switch ( metricType ) {
            case COUNTER: metricType1 = com.memariyan.metricsimulator.spec.dto.MetricType.COUNTER;
            break;
            case SUMMARY: metricType1 = com.memariyan.metricsimulator.spec.dto.MetricType.SUMMARY;
            break;
            case GAUGE: metricType1 = com.memariyan.metricsimulator.spec.dto.MetricType.GAUGE;
            break;
            case TIMER: metricType1 = com.memariyan.metricsimulator.spec.dto.MetricType.TIMER;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + metricType );
        }

        return metricType1;
    }

    protected MetricTag tagToMetricTag(Tag tag) {
        if ( tag == null ) {
            return null;
        }

        MetricTag metricTag = new MetricTag();

        metricTag.setName( tag.getName() );
        metricTag.setValue( tag.getValue() );

        return metricTag;
    }

    protected List<MetricTag> tagListToMetricTagList(List<Tag> list) {
        if ( list == null ) {
            return null;
        }

        List<MetricTag> list1 = new ArrayList<MetricTag>( list.size() );
        for ( Tag tag : list ) {
            list1.add( tagToMetricTag( tag ) );
        }

        return list1;
    }
}
