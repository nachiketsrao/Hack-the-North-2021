import nltk
import sys
import os
import string
import math
import pickle

FILE_MATCHES = 1
SENTENCE_MATCHES = 2


nltk.download('punkt')
nltk.download('stopwords')


def main(company):

    # Calculate IDF values across files
    files = load_files(f"corpus_{company}")
    file_words = {
        filename: tokenize(files[filename])
        for filename in files
    }
    file_idfs = compute_idfs(file_words)

    # Prompt user for query
    query = set(tokenize(input("Query: ")))

    # Determine top file matches according to TF-IDF
    filenames = top_files(query, file_words, file_idfs, n=FILE_MATCHES)

    # Extract sentences from top files
    sentences = dict()
    for filename in filenames:
        for passage in files[filename].split("\n"):
            for sentence in nltk.sent_tokenize(passage):
                tokens = tokenize(sentence)
                if tokens:
                    sentences[sentence] = tokens

    # Compute IDF values across sentences
    idfs = compute_idfs(sentences)

    # Determine top sentence matches
    matches = top_sentences(query, sentences, idfs, n=SENTENCE_MATCHES)
    for match in matches:
        print(match)


def load_files(directory):
    dictionary = {}

    for file in os.listdir(directory):
        with open(os.path.join(directory, file), encoding="utf-8") as ofile:
            dictionary[file] = ofile.read()

    return dictionary


def tokenize(document):

    document = document.lower()
    words = nltk.word_tokenize(document)
    l = []
    for word in words:
        if word not in string.punctuation:
            
            if word not in nltk.corpus.stopwords.words("english"):
                l.append(word)
    return l


def compute_idfs(documents):
    
    idfs = dict()
    num = len(documents.keys())
    words = []
    for word_list in documents.values():
        for word in word_list:
            words.append(word)
    
    for word in words:
        num_doc = 0
        for text in documents.values():
            if word in text:
                num_doc += 1
        idf = math.log(num/num_doc)
        idfs[word] = idf

    return idfs


def top_files(query, files, idfs, n):
    scores = {}
    for filename, filecontent in files.items():
        file_score = 0
        for word in query:
            if word in filecontent:
                file_score += filecontent.count(word) * idfs[word]
        if file_score != 0:
            scores[filename] = file_score

    sorted_by_score = [k for k, v in sorted(scores.items(), key=lambda x: x[1], reverse=True)]
    return sorted_by_score[:n]


def top_sentences(query, sentences, idfs, n):
    
    scores = []

    for sentence in sentences:
        sentence_val = [sentence, 0, 0]
        for word in query:
            if word in sentences[sentence]:
                sentence_val[1] += idfs[word] 
                sentence_val[2] += sentences[sentence].count(word)/len(sentences[sentence])
        scores.append(sentence_val)
    
    return [sentence for sentence, mwm, qtd in sorted(scores, key=lambda item: (item[1], item[2]), reverse=True)][:n]

def learn_text(company):
    try:
        os.makedirs(f"{company}_info")  
    except FileExistsError:
        pass

    files = load_files(f"corpus_{company}")
    file_words = {
        filename: tokenize(files[filename])
        for filename in files
    }
    #creating a binary file to store idfs
    file_idfs = compute_idfs(file_words)
    f = open(f"./{company}_info/file_idfs.dat", "wb")
    pickle.dump(file_idfs, f)
    f.close()

    #creating a binary file to store the file words
    f = open(f"./{company}_info/file_words.dat", "wb")
    pickle.dump(file_words, f)
    f.close()

def ask_questions(company, query):
    files = load_files(f"corpus_{company}")
    f = open(f"./{company}_info/file_idfs.dat", "rb")
    file_idfs = pickle.load(f)
    f.close()

    f = open(f"./{company}_info/file_words.dat", "rb")
    file_words = pickle.load(f)
    f.close()

    query = set(tokenize(query))

    filenames = top_files(query, file_words, file_idfs, n=FILE_MATCHES)

    # Extract sentences from top files
    sentences = dict()
    for filename in filenames:
        for passage in files[filename].split("\n"):
            for sentence in nltk.sent_tokenize(passage):
                tokens = tokenize(sentence)
                if tokens:
                    sentences[sentence] = tokens

    # Compute IDF values across sentences
    idfs = compute_idfs(sentences)

    matches = top_sentences(query, sentences, idfs, n=SENTENCE_MATCHES)
    for match in matches:
        return match