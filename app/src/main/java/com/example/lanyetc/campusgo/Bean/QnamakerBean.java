package com.example.lanyetc.campusgo.Bean;

import java.util.List;

public class QnamakerBean {
    private List<AnswersBean> answers;

    public List<AnswersBean> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswersBean> answers) {
        this.answers = answers;
    }

    public static class AnswersBean {
        /**
         * answer : 可以去F楼寻找&quot;失物招领&quot;
         * questions : ["我丢了东西","我的东西不见了","我丢了钥匙","我丢了钱包","我找到了一个手机","我捡到了一个钱包"]
         * score : 45.011719435453415
         */

        private String answer;
        private double score;
        private List<String> questions;

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public List<String> getQuestions() {
            return questions;
        }

        public void setQuestions(List<String> questions) {
            this.questions = questions;
        }
    }
}
