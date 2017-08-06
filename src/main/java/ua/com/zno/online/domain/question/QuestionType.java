package ua.com.zno.online.domain.question;

/**
 * Created by quento on 02.04.17.
 */
public enum QuestionType {

    //radio button question
    QUESTION_WITH_ONE_CORRECT_ANSWER,

    //checkbox question
    QUESTION_WITH_MULTIPLY_CORRECT_ANSWERS,

    //question with sub_questions (radio button or checkbox) e.g. many-to-many questions-answers
    QUESTION_WITH_SUB_QUESTIONS,

    //question type where user must type answer
    QUESTION_OPEN
}
