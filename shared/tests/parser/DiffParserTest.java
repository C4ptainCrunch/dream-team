package parser;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DiffParserTest {

    @Test
    public void testReference() throws Exception {
        List<String> parserResult = DiffParser.reference("@@ -47,15 +47,15 @@\n" +
                " p%00%00%00\n" +
                "-%07\n" +
                "+#\n" +
                " w%04%00%00%00\n" +
                "-%07\n" +
                "+#\n" +
                " sr%00%13\n" +
                "@@ -946,32 +946,32 @@\n" +
                " 324,9 +324,8 @@%0A\n" +
                "-\n" +
                "  b2c2a);%250A%0A-%250A\n" +
                "@@ -971,9 +971,4965 @@\n" +
                " 0A%0A-%250A%0A\n" +
                "+sq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BDA%03xt%00%EF%BF%BD@@ -232,16 +232,116 @@%0A -67)%257B%257D;%250A%0A+%255Cnode%255Bregular polygon, draw, minimum size=50%255D(043a344b-d5de-4596-88af-fdcb5f298dd1) at (-274,41)%257B%257D;%250A%0A %255Cdraw%255B-%253E%0A@@ -424,9 +424,8 @@%0A b2c2a);%250A%0A-%250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BDO%EF%BF%BDxt%00%25@@ -424,9 +424,8 @@%0A b2c2a);%250A%0A-%250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BD%EF%BF%BDxt%00%EF%BF%BD@@ -424,9 +424,98 @@%0A b2c2a);%250A%0A+%255Cdraw%255B%255D (e8810e0b-979c-4ffe-a7e3-a1f8eb8a5f1d) -- (043a344b-d5de-4596-88af-fdcb5f298dd1);%0A %250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BD,%EF%BF%BDxt%01h@@ -1,82 +1,4 @@%0A-%255Cnode%255Brectangle, draw%255D(699aa9d6-bc09-4586-859d-bde9d5fd2637) at (-233,240)%257B%257D;%250A%0A %255Cnod%0A@@ -65,24 +65,24 @@%0A (68,229)%257B%257D;%250A%0A+%0A %255Cnode%255Bcircle%0A@@ -258,100 +258,8 @@%0A %257B%257D;%250A%0A-%255Cdraw%255B-%253E%255D (699aa9d6-bc09-4586-859d-bde9d5fd2637) -- (214eebd5-f715-4e82-82f3-5990758b2c2a);%250A%0A %255Cdra%0A@@ -344,9 +344,8 @@%0A 98dd1);%250A%0A-%250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BD,%EF%BF%BDxt%00%EF%BF%BD@@ -1,80 +1,4 @@%0A-%255Cnode%255Brectangle, draw%255D(214eebd5-f715-4e82-82f3-5990758b2c2a) at (68,229)%257B%257D;%250A%0A %255Cnod%0A@@ -268,9 +268,8 @@%0A 98dd1);%250A%0A-%250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BD,%EF%BF%BDxt%01%02@@ -1,89 +1,4 @@%0A-%255Cnode%255Bcircle, draw, radius=50%255D(e8810e0b-979c-4ffe-a7e3-a1f8eb8a5f1d) at (-32,-67)%257B%257D;%250A%0A %255Cnod%0A@@ -97,95 +97,4 @@%0A %257B%257D;%250A%0A-%255Cdraw%255B%255D (e8810e0b-979c-4ffe-a7e3-a1f8eb8a5f1d) -- (043a344b-d5de-4596-88af-fdcb5f298dd1);%250A%250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BD,%EF%BF%BDxt%00%EF%BF%BD@@ -1,101 +0,0 @@%0A-%255Cnode%255Bregular polygon, draw, minimum size=50%255D(043a344b-d5de-4596-88af-fdcb5f298dd1) at (-274,41)%257B%257D;%250A%250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BD,%EF%BF%BDxt%00m@@ -1 +1,78 @@%0A+%255Cnode%255Brectangle, draw%255D(699aa9d6-bc09-4586-859d-bde9d5fd2637) at (-233,240)%257B%257D;%0A %250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BD,%EF%BF%BDxt%00%7F@@ -71,9 +71,84 @@%0A 240)%257B%257D;%250A%0A+%255Cnode%255Brectangle, draw%255D(214eebd5-f715-4e82-82f3-5990758b2c2a) at (68,229)%257B%257D;%0A %250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BD,%EF%BF%BDxt%00%EF%BF%BD@@ -147,9 +147,93 @@%0A 229)%257B%257D;%250A%0A+%255Cnode%255Bcircle, draw, radius=50%255D(e8810e0b-979c-4ffe-a7e3-a1f8eb8a5f1d) at (-32,-67)%257B%257D;%0A %250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BD,%EF%BF%BDxt%00%EF%BF%BD@@ -232,9 +232,108 @@%0A -67)%257B%257D;%250A%0A+%255Cnode%255Bregular polygon, draw, minimum size=50%255D(043a344b-d5de-4596-88af-fdcb5f298dd1) at (-274,41)%257B%257D;%0A %250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BD,%EF%BF%BDxt%00%EF%BF%BD@@ -332,9 +332,100 @@%0A ,41)%257B%257D;%250A%0A+%255Cdraw%255B-%253E%255D (699aa9d6-bc09-4586-859d-bde9d5fd2637) -- (214eebd5-f715-4e82-82f3-5990758b2c2a);%0A %250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BD,%EF%BF%BDxt%00%EF%BF%BD@@ -424,9 +424,98 @@%0A b2c2a);%250A%0A+%255Cdraw%255B%255D (e8810e0b-979c-4ffe-a7e3-a1f8eb8a5f1d) -- (043a344b-d5de-4596-88af-fdcb5f298dd1);%0A %250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BD,%EF%BF%BDxt%00%25@@ -514,9 +514,8 @@%0A 98dd1);%250A%0A-%250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BDD%EF%BF%BDxt%01h@@ -1,82 +1,4 @@%0A-%255Cnode%255Brectangle, draw%255D(699aa9d6-bc09-4586-859d-bde9d5fd2637) at (-233,240)%257B%257D;%250A%0A %255Cnod%0A@@ -65,24 +65,24 @@%0A (68,229)%257B%257D;%250A%0A+%0A %255Cnode%255Bcircle%0A@@ -258,100 +258,8 @@%0A %257B%257D;%250A%0A-%255Cdraw%255B-%253E%255D (699aa9d6-bc09-4586-859d-bde9d5fd2637) -- (214eebd5-f715-4e82-82f3-5990758b2c2a);%250A%0A %255Cdra%0A@@ -344,9 +344,8 @@%0A 98dd1);%250A%0A-%250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BDD%EF%BF%BDxt%00%EF%BF%BD@@ -1,80 +1,4 @@%0A-%255Cnode%255Brectangle, draw%255D(214eebd5-f715-4e82-82f3-5990758b2c2a) at (68,229)%257B%257D;%250A%0A %255Cnod%0A@@ -268,9 +268,8 @@%0A 98dd1);%250A%0A-%250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BDD%EF%BF%BDxt%01%02@@ -1,89 +1,4 @@%0A-%255Cnode%255Bcircle, draw, radius=50%255D(e8810e0b-979c-4ffe-a7e3-a1f8eb8a5f1d) at (-32,-67)%257B%257D;%250A%0A %255Cnod%0A@@ -97,95 +97,4 @@%0A %257B%257D;%250A%0A-%255Cdraw%255B%255D (e8810e0b-979c-4ffe-a7e3-a1f8eb8a5f1d) -- (043a344b-d5de-4596-88af-fdcb5f298dd1);%250A%250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BDD%EF%BF%BDxt%00%EF%BF%BD@@ -1,101 +0,0 @@%0A-%255Cnode%255Bregular polygon, draw, minimum size=50%255D(043a344b-d5de-4596-88af-fdcb5f298dd1) at (-274,41)%257B%257D;%250A%250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BDD%EF%BF%BDxt%00m@@ -1 +1,78 @@%0A+%255Cnode%255Brectangle, draw%255D(699aa9d6-bc09-4586-859d-bde9d5fd2637) at (-233,240)%257B%257D;%0A %250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BDD%EF%BF%BDxt%00%7F@@ -71,9 +71,84 @@%0A 240)%257B%257D;%250A%0A+%255Cnode%255Brectangle, draw%255D(214eebd5-f715-4e82-82f3-5990758b2c2a) at (68,229)%257B%257D;%0A %250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BDE%00xt%00%EF%BF%BD@@ -147,9 +147,93 @@%0A 229)%257B%257D;%250A%0A+%255Cnode%255Bcircle, draw, radius=50%255D(e8810e0b-979c-4ffe-a7e3-a1f8eb8a5f1d) at (-32,-67)%257B%257D;%0A %250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BDE%08xt%00%EF%BF%BD@@ -232,9 +232,108 @@%0A -67)%257B%257D;%250A%0A+%255Cnode%255Bregular polygon, draw, minimum size=50%255D(043a344b-d5de-4596-88af-fdcb5f298dd1) at (-274,41)%257B%257D;%0A %250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BDE%10xt%00%EF%BF%BD@@ -332,9 +332,100 @@%0A ,41)%257B%257D;%250A%0A+%255Cdraw%255B-%253E%255D (699aa9d6-bc09-4586-859d-bde9d5fd2637) -- (214eebd5-f715-4e82-82f3-5990758b2c2a);%0A %250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BDE%19xt%00%EF%BF%BD@@ -424,9 +424,98 @@%0A b2c2a);%250A%0A+%255Cdraw%255B%255D (e8810e0b-979c-4ffe-a7e3-a1f8eb8a5f1d) -- (043a344b-d5de-4596-88af-fdcb5f298dd1);%0A %250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BDE%22xt%00%25@@ -514,9 +514,8 @@%0A 98dd1);%250A%0A-%250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BDJ%EF%BF%BDxt%00%EF%BF%BD@@ -332,16 +332,92 @@%0A ,41)%257B%257D;%250A%0A+%255Cnode%255Brectangle, draw%255D(214eebd5-f715-4e82-82f3-5990758b2c2a) at (68,229)%257B%257D;%250A%0A %255Cdraw%255B-%253E%0A@@ -590,9 +590,8 @@%0A 98dd1);%250A%0A-%250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BDJ%EF%BF%BDxt%00%EF%BF%BD@@ -590,9 +590,98 @@%0A 98dd1);%250A%0A+%255Cdraw%255B%255D (214eebd5-f715-4e82-82f3-5990758b2c2a) -- (e8810e0b-979c-4ffe-a7e3-a1f8eb8a5f1d);%0A %250A%0Asq%00~%00%02sq%00~%00%06w%08%00%00%01T%EF%BF%BD%EF%BF%BD%EF%BF%BD%EF%BF%BDxt%00%25@@ -680,9 +680,8 @@%0A a5f1d);%250A%0A-%250A%0A\n" +
                " x");

        Assert.assertEquals("(043a344b-d5de-4596-88af-fdcb5f298dd1)", parserResult.get(0));
    }
}