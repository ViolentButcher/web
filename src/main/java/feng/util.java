package feng;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.hankcs.demo.DemoWord2Vec;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.Vector;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import edu.njupt.feng.web.entity.service.NodeServiceListItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class util {
    /**
     * float[] 转 String
     *
     * @param fs
     * @return String s
     */
    public static String floatList_2_String(float[] fs) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < fs.length; i++) {
            stringBuilder.append(fs[i] + " ");
        }
        String s = stringBuilder.toString();

        return s;
    }

    /**
     * String 转 float[]
     *
     * @param string
     * @return float[]
     */
    public static float[] String_2_floatList(String string) {
        String[] strings = string.split(" ");
        float[] fs = new float[strings.length];
        for (int i = 0; i < strings.length; i++) {
            fs[i] = Float.parseFloat(strings[i]);
        }
        return fs;
    }


    /**
     * 内积（dot）
     * @param vec1
     * @param vec2
     * @return
     */
    public static float dot(float[] vec1,float[] vec2)
    {
        float ret = 0.0f;
        for (int i = 0; i < vec1.length; ++i)
        {
            ret += vec1[i] * vec2[i];
        }
        return ret;
    }

    /**
     * json2String
     *
     * @param str_json
     * @return
     */
    public static Map<String, String> json2map(String str_json) {
        Map<String, String> res = null;
        try {
            Gson gson = new Gson();
            res = gson.fromJson(str_json, new TypeToken<Map<String, String>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
        }
        return res;
    }

    /**
     * map2json
     *
     * @param map
     * @return
     */
    public static String map2json(Map<String, String> map) {
        Gson gson = new Gson();
        String str = gson.toJson(map);

        return str;
    }

    /**
     * 加载预料，获得文档向量模型
     *
     * @return
     * @throws IOException
     */
    public static DocVectorModel get_DocVectorModel() throws IOException {
        DemoWord2Vec demoWord2Vec = new DemoWord2Vec();
        //词向量
        WordVectorModel wordVectorModel = demoWord2Vec.trainOrLoadModel();
        //文档向量
        return new DocVectorModel(wordVectorModel);
    }

    /**
     * 未加处理的文档 ==> float[]类型的向量
     *
     * @param doc
     * @param docVectorModel
     * @return float[]
     */
    public static float[] doc2vec(DocVectorModel docVectorModel, String doc) {
        return docVectorModel.query(doc).getElementArray();
    }

    /**
     * 未加处理的文档 ==> Vector类型的向量
     *
     * @param doc
     * @param docVectorModel
     * @return float[]
     */
    public static Vector doc2Vector(DocVectorModel docVectorModel, String doc){
        return docVectorModel.query(doc);
    }
    /**
     * 计算两个文本的相似度
     *
     * @param docVectorModel
     * @param doc1
     * @param doc2
     * @return
     */
    public static float similarity(DocVectorModel docVectorModel, String doc1, String doc2) {
        return docVectorModel.similarity(doc1, doc2);
    }

}
