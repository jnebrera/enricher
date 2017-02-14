package io.wizzie.ks.query;

import io.wizzie.ks.query.antlr4.Join;
import io.wizzie.ks.query.antlr4.Query;
import io.wizzie.ks.query.antlr4.Select;
import io.wizzie.ks.query.antlr4.Stream;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class EnricherCompilerUnitTest {

    @Test
    public void ShouldParseSimpleQueryCorrectly() {

        String stringQuery = "SELECT * FROM STREAM rb_input " +
                "JOIN SELECT a,b FROM STREAM rb_input2 USING jClass " +
                "INSERT INTO TABLE rb_output";

        Query query = EnricherCompiler.parse(stringQuery);

        assertNotNull(query);

        // Select tests
        Select selectObject = query.getSelect();

        assertNotNull(selectObject);

        List<String> selectDimensions = selectObject.getDimensions();

        assertNotNull(selectDimensions);
        assertEquals(1, selectDimensions.size());
        assertEquals(Collections.singletonList("*"), selectDimensions);

        List<Stream> selectStreams = selectObject.getStreams();

        assertNotNull(selectStreams);
        assertEquals(1, selectStreams.size());
        assertEquals("rb_input", selectStreams.get(0).getName());
        assertFalse(selectStreams.get(0).isTable());

        // Joins tests
        List<Join> joins = query.getJoins();

        assertEquals(1, joins.size());

        List<String> joinDimensions = joins.get(0).getDimensions();
        assertNotNull(joinDimensions);
        assertEquals(2, joinDimensions.size());
        assertEquals(Arrays.asList("a", "b"), joinDimensions);

        Stream joinStream = joins.get(0).getStream();

        assertNotNull(joinStream);
        assertEquals("rb_input2", joinStream.getName());
        assertFalse(joinStream.isTable());

        String joinerClass = joins.get(0).getJoinerName();

        assertNotNull(joinerClass);
        assertEquals("jClass", joinerClass);

        // Insert tests
        Stream insertObject = query.getInsert();

        assertNotNull(insertObject);
        assertEquals("rb_output", insertObject.getName());
        assertTrue(insertObject.isTable());

    }

    @Test
    public void ShouldParseComplexQueryCorrectly() {

        String stringQuery = "SELECT * FROM STREAM rb_input " +
                "JOIN SELECT a,b,c FROM STREAM rb_input2 USING jClass1 " +
                "JOIN SELECT * FROM TABLE rb_input3 USING jClass2 " +
                "JOIN SELECT x,y FROM STREAM rb_input4 USING jClass3 " +
                "INSERT INTO TABLE rb_output";

        Query query = EnricherCompiler.parse(stringQuery);

        assertNotNull(query);

        // Select tests
        Select selectObject = query.getSelect();

        assertNotNull(selectObject);

        List<String> selectDimensions = selectObject.getDimensions();

        assertNotNull(selectDimensions);
        assertEquals(1, selectDimensions.size());
        assertEquals(Collections.singletonList("*"), selectDimensions);

        List<Stream> selectStreams = selectObject.getStreams();

        assertNotNull(selectStreams);
        assertEquals(1, selectStreams.size());
        assertEquals("rb_input", selectStreams.get(0).getName());
        assertFalse(selectStreams.get(0).isTable());

        // Joins tests
        List<Join> joins = query.getJoins();

        assertEquals(3, joins.size());

        // Join 1
        List<String> joinDimensions1 = joins.get(0).getDimensions();

        assertNotNull(joinDimensions1);
        assertEquals(3, joinDimensions1.size());
        assertEquals(Arrays.asList("a", "b", "c"), joinDimensions1);

        Stream joinStream1 = joins.get(0).getStream();

        assertNotNull(joinStream1);
        assertEquals("rb_input2", joinStream1.getName());
        assertFalse(joinStream1.isTable());

        String joinerClass1 = joins.get(0).getJoinerName();

        assertNotNull(joinerClass1);
        assertEquals("jClass1", joinerClass1);

        // Join 2
        List<String> joinDimensions2 = joins.get(1).getDimensions();

        assertNotNull(joinDimensions2);
        assertEquals(1, joinDimensions2.size());
        assertEquals(Collections.singletonList("*"), joinDimensions2);

        Stream joinStream2 = joins.get(1).getStream();

        assertNotNull(joinStream2);
        assertEquals("rb_input3", joinStream2.getName());
        assertTrue(joinStream2.isTable());

        String joinerClass2 = joins.get(1).getJoinerName();

        assertNotNull(joinerClass2);
        assertEquals("jClass2", joinerClass2);


        // Join 3
        List<String> joinDimensions3 = joins.get(2).getDimensions();

        assertNotNull(joinDimensions3);
        assertEquals(2, joinDimensions3.size());
        assertEquals(Arrays.asList("x", "y"), joinDimensions3);

        Stream joinStream3 = joins.get(2).getStream();

        assertNotNull(joinStream3);
        assertEquals("rb_input4", joinStream3.getName());
        assertFalse(joinStream3.isTable());

        String joinerClass3 = joins.get(2).getJoinerName();

        assertNotNull(joinerClass3);
        assertEquals("jClass3", joinerClass3);

        // Insert tests
        Stream insertObject = query.getInsert();

        assertNotNull(insertObject);
        assertEquals("rb_output", insertObject.getName());
        assertTrue(insertObject.isTable());

    }

    @Test
    public void ShouldParseQueryWithEnrichWithCorrectly() {

        String stringQuery = "SELECT * FROM STREAM rb_input " +
                "JOIN SELECT a,b FROM STREAM rb_input2 USING jClass " +
                "ENRICH WITH pkg2classA " +
                "ENRICH WITH pkg1classB " +
                "INSERT INTO TABLE rb_output";

        Query query = EnricherCompiler.parse(stringQuery);

        assertNotNull(query);

        // Select tests
        Select selectObject = query.getSelect();

        assertNotNull(selectObject);

        List<String> selectDimensions = selectObject.getDimensions();

        assertNotNull(selectDimensions);
        assertEquals(1, selectDimensions.size());
        assertEquals(Collections.singletonList("*"), selectDimensions);

        List<Stream> selectStreams = selectObject.getStreams();

        assertNotNull(selectStreams);
        assertEquals(1, selectStreams.size());
        assertEquals("rb_input", selectStreams.get(0).getName());
        assertFalse(selectStreams.get(0).isTable());

        // Joins tests
        List<Join> joins = query.getJoins();

        assertEquals(1, joins.size());

        List<String> joinDimensions = joins.get(0).getDimensions();
        assertNotNull(joinDimensions);
        assertEquals(2, joinDimensions.size());
        assertEquals(Arrays.asList("a", "b"), joinDimensions);

        Stream joinStream = joins.get(0).getStream();

        assertNotNull(joinStream);
        assertEquals("rb_input2", joinStream.getName());
        assertFalse(joinStream.isTable());

        String joinerClass = joins.get(0).getJoinerName();

        assertNotNull(joinerClass);
        assertEquals("jClass", joinerClass);

        // Enrich with tests
        List<String> enrichWiths = query.getEnrichWiths();

        assertNotNull(enrichWiths);

        assertEquals(2, enrichWiths.size());

        String enrichWith1 = enrichWiths.get(0);

        assertNotNull(enrichWith1);

        assertEquals("pkg2classA", enrichWith1);

        String enrichWith2 = enrichWiths.get(1);

        assertNotNull(enrichWith2);

        assertEquals("pkg1classB", enrichWith2);

        // Insert tests
        Stream insertObject = query.getInsert();

        assertNotNull(insertObject);
        assertEquals("rb_output", insertObject.getName());
        assertTrue(insertObject.isTable());

    }
}