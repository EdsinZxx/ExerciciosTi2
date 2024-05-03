import com.azure.core.credential.AzureKeyCredential;
import com.azure.ai.formrecognizer.DocumentAnalysisClient;
import com.azure.ai.formrecognizer.models.AnalyzeDocumentOperation;
import com.azure.ai.formrecognizer.models.AnalyzeResult;
import com.azure.ai.formrecognizer.models.DocumentAnalysisClientBuilder;
import com.azure.ai.formrecognizer.models.DocumentPage;
import com.azure.ai.formrecognizer.models.DocumentLine;
import com.azure.ai.formrecognizer.models.DocumentStyle;
import com.azure.ai.formrecognizer.models.DocumentSpan;
import com.azure.ai.formrecognizer.models.DocumentLanguage;
import com.azure.core.util.polling.SyncPoller;

import java.net.URL;

public class FormRecognizerSample {
    public static void main(String[] args) {
        // Replace with your Form Recognizer endpoint and key
        String endpoint = "YOUR_FORM_RECOGNIZER_ENDPOINT";
        String key = "YOUR_FORM_RECOGNIZER_KEY";
        AzureKeyCredential credential = new AzureKeyCredential(key);

        // Create the Form Recognizer client
        DocumentAnalysisClient client = new DocumentAnalysisClientBuilder()
                .credential(endpoint, credential)
                .buildClient();

        // Sample document URL
        String fileUrl = "https://raw.githubusercontent.com/Azure-Samples/cognitive-services-REST-api-samples/master/curl/form-recognizer/rest-api/read.png";

        // Analyze the document
        SyncPoller<AnalyzeDocumentOperation, AnalyzeResult> operation = client.beginAnalyzeDocumentFromUrl("prebuilt-read", fileUrl);
        AnalyzeResult result = operation.getFinalResult();

        for (DocumentPage page : result.getPages()) {
            System.out.println("Document Page " + page.getPageNumber() + " has " + page.getLines().size() + " line(s), " +
                    page.getWords().size() + " word(s)");

            for (int i = 0; i < page.getLines().size(); i++) {
                DocumentLine line = page.getLines().get(i);
                System.out.println("  Line " + i + " has content: '" + line.getContent() + "'");
                System.out.println("    Its bounding box is:");
                System.out.println("      Upper left => X: " + line.getBoundingPoly().get(0).getX() + ", Y= " +
                        line.getBoundingPoly().get(0).getY());
                System.out.println("      Upper right => X: " + line.getBoundingPoly().get(1).getX() + ", Y= " +
                        line.getBoundingPoly().get(1).getY());
                System.out.println("      Lower right => X: " + line.getBoundingPoly().get(2).getX() + ", Y= " +
                        line.getBoundingPoly().get(2).getY());
                System.out.println("      Lower left => X: " + line.getBoundingPoly().get(3).getX() + ", Y= " +
                        line.getBoundingPoly().get(3).getY());
            }
        }

        for (DocumentStyle style : result.getStyles()) {
            boolean isHandwritten = style.getIsHandwritten() != null && style.getIsHandwritten();
            if (isHandwritten && style.getConfidence() > 0.8) {
                System.out.println("Handwritten content found:");
                for (DocumentSpan span : style.getSpans()) {
                    System.out.println("  Content: " + result.getContent().substring(span.getIndex(), span.getLength()));
                }
            }
        }

        for (DocumentLanguage language : result.getLanguages()) {
            System.out.println("Found language '" + language.getLocale() + "' with confidence " + language.getConfidence());
        }
    }
}
