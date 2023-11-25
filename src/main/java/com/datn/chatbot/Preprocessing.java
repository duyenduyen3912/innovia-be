package com.datn.chatbot;

import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.*;
import java.io.BufferedReader;


public class Preprocessing {
	List<String> questions = new ArrayList<>();
    List<String> answers = new ArrayList<>();
    
    // read data
	public static List<String> readDataFromFile(String filePath) throws IOException {
		List<String> lines = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line;
		
		while ((line = reader.readLine()) != null) {
			lines.add(line);
		}
		reader.close();
		return lines;
	}
	
	// split answer and question
	public  void splitData(List<String> data) {
        for (String line : data) {
            // Tách câu hỏi và câu trả lời dựa trên dấu phân tách "Q:" và "A:"
            String[] parts = line.split("Q:|A:");
            if (parts.length == 3) {
                this.questions.add(parts[1].trim());
                this.answers.add(parts[2].trim());
            }
        }

        // In kết quả
        for (int i = 0; i < questions.size(); i++) {
            System.out.println("Câu hỏi: " + questions.get(i));
            System.out.println("Câu trả lời: " + answers.get(i));
            System.out.println("-----------");
        }
    }
	
	
	// process
	public static String preprocessText(String text) {
        // Chuyển về chữ thường
        text = text.toLowerCase();
        // Loại bỏ dấu tiếng Việt
        text = Normalizer.normalize(text, Normalizer.Form.NFD);
        text = text.replaceAll("[^\\p{ASCII}]", "");
        // Loại bỏ ký tự không mong muốn
        text = text.replaceAll("[^a-z0-9?.!, ]", "");
        return text;
    }
	
	// tokenize
	
	public static List<List<String>> tokenizeQA(List<String> questions, List<String> answers) {
        List<List<String>> tokenizedQA = new ArrayList<>();
        Set<String> vocabulary = new HashSet<>();
        for (int i = 0; i < questions.size(); i++) {
            String question = questions.get(i);
            String answer = answers.get(i);

            List<String> tokenizedQuestion = tokenizeSentence(question);
            List<String> tokenizedAnswer = tokenizeSentence(answer);

         // Thêm token đặc biệt cho câu hỏi
            tokenizedQuestion.add(0, "<sos>");  
            tokenizedQuestion.add("<eos>");       

            // Thêm token đặc biệt cho câu trả lời
            tokenizedAnswer.add(0, "<sos>");    
            tokenizedAnswer.add("<eos>");         

            // cập nhật từ điển
            vocabulary.addAll(tokenizedQuestion);
            vocabulary.addAll(tokenizedAnswer);
            
            tokenizedQA.add(tokenizedQuestion);
            tokenizedQA.add(tokenizedAnswer);
            
            
        }
        List<String> uniqueWords = new ArrayList<>(vocabulary);

        // In từ điển
        System.out.println("Vocabulary: " + uniqueWords);
        return tokenizedQA;
    }
	
	public static List<String> tokenizeSentence(String sentence) {
        StringTokenizer tokenizer = new StringTokenizer(sentence.toLowerCase(), "\\s+");
        List<String> tokens = new ArrayList<>();

        while (tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());
        }

        return tokens;
    }
	
	// convert
	public static List<List<Integer>> convertToIndices(List<List<String>> tokenizedQA, List<String> vocabulary) {
        List<List<Integer>> indexedQA = new ArrayList<>();

        // Tạo một ánh xạ từ từ điển
        Map<String, Integer> wordToIndex = new HashMap<>();
        for (int i = 0; i < vocabulary.size(); i++) {
            wordToIndex.put(vocabulary.get(i), i);
        }

        for (List<String> tokenizedSentence : tokenizedQA) {
            List<Integer> indices = new ArrayList<>();

            for (String token : tokenizedSentence) {
                // Nếu từ không có trong từ điển, sử dụng một chỉ số đặc biệt (ví dụ: -1)
                int index = wordToIndex.containsKey(token) ? wordToIndex.get(token) : -1;
                indices.add(index);
            }

            indexedQA.add(indices);
        }

        return indexedQA;
    }
	
	// padding
	public static List<List<Integer>> padSequences(List<List<Integer>> indexedQA, int maxLength) {
        List<List<Integer>> paddedQA = new ArrayList<>();

        for (List<Integer> indices : indexedQA) {
            // Nếu chuỗi quá dài, cắt bớt
            if (indices.size() > maxLength) {
                indices = indices.subList(0, maxLength);
            }
            // Nếu chuỗi quá ngắn, thêm padding (ví dụ: 0) vào cuối
            while (indices.size() < maxLength) {
                indices.add(0);
            }
            paddedQA.add(indices);
        }

        return paddedQA;
    }
	
	// split Data train, test
	public static List<List<Integer>> splitData(List<List<Integer>> paddedQA, double trainPercentage) {
        List<List<Integer>> trainingData = new ArrayList<>();
        List<List<Integer>> testData = new ArrayList<>();

        // Số lượng mẫu dữ liệu cho tập huấn luyện
        int trainSize = (int) (paddedQA.size() * trainPercentage);

        for (int i = 0; i < paddedQA.size(); i++) {
            if (i < trainSize) {
                trainingData.add(paddedQA.get(i));
            } else {
                testData.add(paddedQA.get(i));
            }
        }

        List<List<Integer>> result = new ArrayList<>();
        result.addAll(trainingData);
        result.addAll(testData);

        return result;
    }
	
	// convert to one hot coding
	public static List<List<Integer>> oneHotEncode(List<List<Integer>> sequences, int vocabSize) {
        List<List<Integer>> oneHotEncoded = new ArrayList<>();

        for (List<Integer> sequence : sequences) {
            List<Integer> oneHotSequence = new ArrayList<>();

            for (int index : sequence) {
                // Tạo vectơ one-hot với chiều dài là vocabSize
                List<Integer> oneHotVector = new ArrayList<>(Collections.nCopies(vocabSize, 0));

                // Đặt chỉ số tương ứng với index thành 1
                if (index != -1) {
                    oneHotVector.set(index, 1);
                }

                // Thêm vectơ one-hot vào chuỗi
                oneHotSequence.addAll(oneHotVector);
            }

            oneHotEncoded.add(oneHotSequence);
        }

        return oneHotEncoded;
    }
	
	public  void main(String[] args) {
		String filePath = "data\\chatbot_data.txt";
		try {
			List<String> data = readDataFromFile(filePath);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
