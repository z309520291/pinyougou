package com;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class TestLucene {
@Test
    public void createIndex1() throws Exception {
        //1������һ��Director����ָ�������Ᵽ���λ�á�
        //�������Ᵽ�����ڴ���
        //Directory directory = new RAMDirectory();
        //�������Ᵽ���ڴ���
        Directory directory = FSDirectory.open(new File("D:\\��ܽ׶�").toPath());
        //2������Directory���󴴽�һ��IndexWriter����
        IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig());
        //3����ȡ�����ϵ��ļ�����Ӧÿ���ļ�����һ���ĵ�����
        File dir = new File("D:\\��ܽ׶�\\A0.lucene2018\\02.�ο�����\\searchsource");
        File[] files = dir.listFiles();
        for (File f : files) {
            //ȡ�ļ���
            String fileName = f.getName();
            //�ļ���·��
            String filePath = f.getPath();
            //�ļ�������
            String fileContent = FileUtils.readFileToString(f, "utf-8");
            //�ļ��Ĵ�С
            long fileSize = FileUtils.sizeOf(f);
            //����Field
            //����1��������ƣ�����2��������ݣ�����3���Ƿ�洢
            Field fieldName = new TextField("filename", fileName, Field.Store.YES);
            Field fieldPath = new TextField("path", filePath, Field.Store.YES);
            Field fieldContent = new TextField("content", fileContent, Field.Store.YES);
            Field fieldSize = new TextField("size", fileSize + "", Field.Store.YES);
            //�����ĵ�����
            Document document = new Document();
            //���ĵ������������
            document.add(fieldName);
            document.add(fieldPath);
            document.add(fieldContent);
            document.add(fieldSize);
            //5�����ĵ�����д��������
            indexWriter.addDocument(document);

        }
        //6���ر�indexwriter����
        indexWriter.close();
    }

@Test
    public void test1() throws IOException {
        //ָ���������ŵ�·��
        //D:\temp\index
        Directory directory = FSDirectory.open(new File("D:\\��ܽ׶�").toPath());
        //����indexReader����
        IndexReader indexReader = DirectoryReader.open(directory);
        //����indexsearcher����
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //������ѯ
        Query query = new TermQuery(new Term("filename", "apache"));
        //ִ�в�ѯ
        //��һ�������ǲ�ѯ���󣬵ڶ��������ǲ�ѯ������ص����ֵ
        TopDocs topDocs = indexSearcher.search(query, 10);
        //��ѯ�����������
        System.out.println("��ѯ�������������"+ topDocs.totalHits);
        //������ѯ���
        //topDocs.scoreDocs�洢��document�����id
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            //scoreDoc.doc���Ծ���document�����id
            //����document��id�ҵ�document����
            Document document = indexSearcher.doc(scoreDoc.doc);
            System.out.println(document.get("filename"));
            //System.out.println(document.get("content"));
            System.out.println(document.get("path"));
            System.out.println(document.get("size"));
            System.out.println("-------------------------");
        }
        //�ر�indexreader����
        indexReader.close();

    }

    @Test
    public void testTokenStream() throws Exception {
        //����һ����׼����������
        Analyzer analyzer = new StandardAnalyzer();
        //���tokenStream����
        //��һ����������������������һ��
        //�ڶ���������Ҫ�������ı�����
        TokenStream tokenStream = analyzer.tokenStream("test", "The Spring Framework provides a comprehensive programming and configuration model.");
        //���һ�����ã����Ի��ÿ���ؼ���
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        //���һ��ƫ���������ã���¼�˹ؼ��ʵĿ�ʼλ���Լ�����λ��
        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
        //��ָ��������б��ͷ��
        tokenStream.reset();
        //�����ؼ����б�ͨ��incrementToken�����ж��б��Ƿ����
        while(tokenStream.incrementToken()) {
            //�ؼ��ʵ���ʼλ��
            System.out.println("start->" + offsetAttribute.startOffset());
            //ȡ�ؼ���
            System.out.println(charTermAttribute);
            //����λ��
            System.out.println("end->" + offsetAttribute.endOffset());
        }
        tokenStream.close();
    }

    @Test
    public void addDocument() throws Exception {
        //��������·��
        Directory directory = FSDirectory.open(new File("D:\\��ܽ׶�").toPath());
        IndexWriterConfig config = new IndexWriterConfig(new IKAnalyzer());
        //����һ��indexwriter����
        IndexWriter indexWriter = new IndexWriter(directory, config);
//...
    }

}
