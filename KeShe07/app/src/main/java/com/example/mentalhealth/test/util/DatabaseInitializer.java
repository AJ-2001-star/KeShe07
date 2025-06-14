package com.example.mentalhealth.test.util;

import android.content.Context;


import com.example.mentalhealth.test.data.Conclusion;
import com.example.mentalhealth.test.data.ConclusionDao;
import com.example.mentalhealth.test.data.Question;
import com.example.mentalhealth.test.data.QuestionDao;
import com.example.mentalhealth.test.data.Questionnaire;
import com.example.mentalhealth.test.data.QuestionnaireDao;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseInitializer {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void populateAsync(@NotNull Context context) {
        executor.execute(() -> {
            QuestionnaireDao questionnaireDao = new QuestionnaireDao(context);
            QuestionDao questionDao = new QuestionDao(context);
            ConclusionDao conclusionDao = new ConclusionDao(context);

            // 如果问卷表为空，则初始化数据
            if (questionnaireDao.getAllQuestionnaires().isEmpty()) {
                insertQuestionnaireA1(questionnaireDao, questionDao, conclusionDao);
                insertQuestionnaireA2(questionnaireDao, questionDao, conclusionDao);
                insertQuestionnaireA3(questionnaireDao, questionDao, conclusionDao);
                insertQuestionnaireA4(questionnaireDao, questionDao, conclusionDao);
            }
        });
    }

    private static void insertQuestionnaireA1(QuestionnaireDao qDao, QuestionDao qnDao, ConclusionDao cDao) {
        // 插入问卷 a1
        Questionnaire q1 = new Questionnaire("a1", "汉密尔顿专业版抑郁测试", 10);
        qDao.insert(q1);

        // 插入题目 a1
        qnDao.insert(new Question("a1",
                "1.抑郁情绪",
                "只在问到时才诉述",
                "在谈话中自发地表达",
                "不用言语也可以流露出这种表情",
                "不喜欢",
                1, 2, 3, 4));

        qnDao.insert(new Question("a1",
                "2.有罪感",
                "感到自己已连累他人",
                "反复思考以往的过失和错误",
                "有罪恶妄想",
                "伴指责或威胁性幻觉",
                1, 2, 3, 4));

        qnDao.insert(new Question("a1",
                "3. 自杀",
                "觉得活着没有意思",
                "常想到与死有关的事",
                "消极观念（自杀观念）",
                "有严重自杀行为",
                1, 2, 3, 4));

        qnDao.insert(new Question("a1",
                "4. 睡眠不深",
                "主诉有时有入睡困难",
                "睡眠不深",
                "睡眠浅多恶梦",
                "主诉每晚均入睡困难",
                1, 2, 3, 4));

        qnDao.insert(new Question("a1",
                "5. 早醒",
                "有早醒，但能重新入睡",
                "早醒后无法重新入睡",
                "没有早醒",
                "经常在半夜早醒",
                1, 2, 3, 4));

        qnDao.insert(new Question("a1",
                "6、激越",
                "检查时有些心神不定",
                "明显的心神不定或小动作多",
                "不能静坐，检查中曾起立",
                "搓手、咬手指、扯头发",
                1, 2, 3, 4));

        qnDao.insert(new Question("a1",
                "7. 精神性焦虑",
                "问及时诉述",
                "自发地表达",
                "表情和言谈流露出明显的忧虑",
                "明显惊恐",
                1, 2, 3, 4));

        qnDao.insert(new Question("a1",
                "8. 疑病",
                "对身体过分关注",
                "反复思考健康问题",
                "有疑病妄想",
                "伴幻觉的疑病妄想",
                1, 2, 3, 4));

        qnDao.insert(new Question("a1",
                "9. 偏执症状",
                "有猜疑",
                "有牵连观念",
                "有关系妄想或被害妄想",
                "伴有幻觉的关系妄想或被害妄想",
                1, 2, 3, 4));

        qnDao.insert(new Question("a1",
                "10. 人格解体或现实解体",
                "问及时才诉述",
                "自发诉述",
                "有虚无妄想",
                "伴幻觉的虚无妄想",
                1, 2, 3, 4));

        // 插入结论 a1
        cDao.insert(new Conclusion( "a1", 0, 10,
                "您的心理状况一切正常。感谢您做这次心理评测。"));
        cDao.insert(new Conclusion( "a1", 11,20,
                "您的举止仪表、言谈接触，外表看来并无异常表现，但通过该问卷可知，这只是一种愉快乐观的假象。您的内心有痛苦悲观、多思多虑、自卑消极、无法自行排除的精力、体力、脑力的下降和严重顽固的失眠，多种躯体不适等征象。建议您进一步深入地做精神检查和心理测定。"));
        cDao.insert(new Conclusion( "a1", 21,30,
                "您出现了顽固持久、久治难愈的以失眠为中心睡眠障碍，并且表现出了心境低落，兴趣和愉快感丧失，容易疲劳。通过该问卷结果可知，您极有可能患上了轻度抑郁症，希望您能及时去医院接受心理辅导和治疗。"));
        cDao.insert(new Conclusion( "a1", 31, 100,
                "您出现了心情差、情绪提不起来、明显的精神反应迟钝、再有就是出现厌食然后体重因此下降，还有就是罪恶感不断加深，这已经达到了重度抑郁症发作标准，建议您立马去医院接受心理辅导和治疗。"));
    }

    private static void insertQuestionnaireA2(QuestionnaireDao qDao, QuestionDao qnDao, ConclusionDao cDao) {
        Questionnaire q = new Questionnaire("a2", "SDS专业版抑郁症自测", 10);
        qDao.insert(q);

        qnDao.insert(new Question("a2",
                "1.抑郁情绪",
                "只在问到时才诉述",
                "在谈话中自发地表达",
                "不用言语也可以流露出这种表情",
                "不喜欢",
                1, 2, 3, 4));
        qnDao.insert(new Question("a2",
                "2.有罪感",
                "感到自己已连累他人",
                "反复思考以往的过失和错误",
                "有罪恶妄想",
                "罪恶妄想伴有指责或威胁性幻觉",
                1, 2, 3, 4));
        qnDao.insert(new Question("a2",
                "3. 自杀",
                "觉得活着没有意思",
                "常想到与死有关的事",
                "消极观念（自杀观念）",
                "有严重自杀行为",
                1, 2, 3, 4));
        qnDao.insert(new Question("a2",
                "4. 睡眠不深",
                "主诉有时有入睡困难",
                "睡眠不深",
                "睡眠浅多恶梦",
                "主诉每晚均入睡困难",
                1, 2, 3, 4));
        qnDao.insert(new Question("a2",
                "5. 早醒",
                "有早醒，但能重新入睡",
                "早醒后无法重新入睡",
                "没有早醒",
                "经常在半夜早醒",
                1, 2, 3, 4));
        qnDao.insert(new Question("a2",
                "6、激越",
                "检查时有些心神不定",
                "明显的心神不定或小动作多",
                "不能静坐，检查中曾起立",
                "搓手、咬手指、扯头发",
                1, 2, 3, 4));
        qnDao.insert(new Question("a2",
                "7. 精神性焦虑",
                "问及时诉述",
                "自发地表达",
                "表情和言谈流露出明显的忧虑",
                "明显惊恐",
                1, 2, 3, 4));
        qnDao.insert(new Question("a2",
                "8. 疑病",
                "对身体过分关注",
                "反复思考健康问题",
                "有疑病妄想",
                "伴幻觉的疑病妄想",
                1, 2, 3, 4));
        qnDao.insert(new Question("a2",
                "9. 偏执症状",
                "有猜疑",
                "有牵连观念",
                "有关系妄想或被害妄想",
                "伴有幻觉的关系妄想或被害妄想",
                1, 2, 3, 4));
        qnDao.insert(new Question("a2",
                "10. 人格解体或现实解体",
                "问及时才诉述",
                "自发诉述",
                "有虚无妄想",
                "伴幻觉的虚无妄想",
                1, 2, 3, 4));

        // 插入结论 a2
        cDao.insert(new Conclusion("a2", 0,10, "第一档：正常"));
        cDao.insert(new Conclusion( "a2", 11, 20, "第二档：可能有抑郁症"));
        cDao.insert(new Conclusion( "a2", 21, 30, "第三档：肯定有抑郁症"));
        cDao.insert(new Conclusion( "a2", 31, 100, "第四档：严重抑郁症"));
    }

    private static void insertQuestionnaireA3(QuestionnaireDao qDao, QuestionDao qnDao, ConclusionDao cDao) {
        Questionnaire q = new Questionnaire("a3", "焦虑程度测试", 10);
        qDao.insert(q);

        qnDao.insert(new Question("a3",
                "1.焦虑情绪",
                "只在问到时才诉述",
                "在谈话中自发地表达",
                "不用言语也可以流露出这种表情",
                "不喜欢",
                1, 2, 3, 4));
        qnDao.insert(new Question("a3",
                "2.有罪感",
                "感到自己已连累他人",
                "反复思考以往的过失和错误",
                "有罪恶妄想",
                "罪恶妄想伴有指责或威胁性幻觉",
                1, 2, 3, 4));
        qnDao.insert(new Question("a3",
                "3. 自杀",
                "觉得活着没有意思",
                "常想到与死有关的事",
                "消极观念（自杀观念）",
                "有严重自杀行为",
                1, 2, 3, 4));
        qnDao.insert(new Question("a3",
                "4. 睡眠不深",
                "主诉有时有入睡困难",
                "睡眠不深",
                "睡眠浅多恶梦",
                "主诉每晚均入睡困难",
                1, 2, 3, 4));
        qnDao.insert(new Question("a3",
                "5. 早醒",
                "有早醒，但能重新入睡",
                "早醒后无法重新入睡",
                "没有早醒",
                "经常在半夜早醒",
                1, 2, 3, 4));
        qnDao.insert(new Question("a3",
                "6、激越",
                "检查时有些心神不定",
                "明显的心神不定或小动作多",
                "不能静坐，检查中曾起立",
                "搓手、咬手指、扯头发",
                1, 2, 3, 4));
        qnDao.insert(new Question("a3",
                "7. 精神性焦虑",
                "问及时诉述",
                "自发地表达",
                "表情和言谈流露出明显的忧虑",
                "明显惊恐",
                1, 2, 3, 4));
        qnDao.insert(new Question("a3",
                "8. 疑病",
                "对身体过分关注",
                "反复思考健康问题",
                "有疑病妄想",
                "伴幻觉的疑病妄想",
                1, 2, 3, 4));
        qnDao.insert(new Question("a3",
                "9. 偏执症状",
                "有猜疑",
                "有牵连观念",
                "有关系妄想或被害妄想",
                "伴有幻觉的关系妄想或被害妄想",
                1, 2, 3, 4));
        qnDao.insert(new Question("a3",
                "10. 人格解体或现实解体",
                "问及时才诉述",
                "自发诉述",
                "有虚无妄想",
                "伴幻觉的虚无妄想",
                1, 2, 3, 4));

        // 插入结论 a3
        cDao.insert(new Conclusion( "a3", 0,10, "第一档：正常"));
        cDao.insert(new Conclusion( "a3", 11, 20, "第二档：可能有焦虑症"));
        cDao.insert(new Conclusion( "a3", 21, 30, "第三档：肯定有焦虑症"));
        cDao.insert(new Conclusion( "a3", 31, 100, "第四档：严重焦虑症"));
    }

    private static void insertQuestionnaireA4(QuestionnaireDao qDao, QuestionDao qnDao, ConclusionDao cDao) {
        Questionnaire q = new Questionnaire("a4", "考试焦虑情绪测试", 10);
        qDao.insert(q);

        // 插入题目 a4
        qnDao.insert(new Question("a4",
                "1. 考试前你会感到紧张或不安吗？",
                "几乎不紧张", "偶尔会紧张", "经常感到紧张", "非常紧张，影响睡眠", 1, 2, 3, 4));

        qnDao.insert(new Question("a4",
                "2. 面对考试时，你的注意力能否集中？",
                "完全能集中", "基本可以集中", "容易走神", "完全无法集中", 1, 2, 3, 4));

        qnDao.insert(new Question("a4",
                "3. 考试当天是否会出现身体不适（如心跳加速、出汗等）？",
                "完全没有", "轻微不适", "明显症状", "严重不适，难以应对", 1, 2, 3, 4));

        qnDao.insert(new Question("a4",
                "4. 考试中遇到难题时，你是否会立刻慌乱？",
                "不会慌乱", "稍有慌张", "容易慌乱", "完全失控", 1, 2, 3, 4));

        qnDao.insert(new Question("a4",
                "5. 考试成绩对你来说意味着什么？",
                "只是参考", "比较重要", "非常重要", "决定人生走向", 1, 2, 3, 4));

        qnDao.insert(new Question("a4",
                "6. 你是否经常担心自己考不好？",
                "从不担心", "偶尔担心", "经常担心", "总是提心吊胆", 1, 2, 3, 4));

        qnDao.insert(new Question("a4",
                "7. 考试期间你是否出现失眠或睡眠质量下降？",
                "没有变化", "轻微影响", "明显影响", "彻夜难眠", 1, 2, 3, 4));

        qnDao.insert(new Question("a4",
                "8. 考试后你是否会反复回想考试过程？",
                "很少回想", "有时回想", "频繁回想", "整日沉浸在回忆中", 1, 2, 3, 4));

        qnDao.insert(new Question("a4",
                "9. 如果考试没准备好，你是否觉得天塌了？",
                "不会这样想", "有点担忧", "感觉很糟糕", "认为一切都完了", 1, 2, 3, 4));

        qnDao.insert(new Question("a4",
                "10. 考试失败会让你觉得自己毫无价值吗？",
                "不会这么认为", "有些自卑", "常常自责", "完全否定自己", 1, 2, 3, 4));

        cDao.insert(new Conclusion("a4", 0, 10, "您的心理状况一切正常。感谢您做这次心理评测。"));
        cDao.insert(new Conclusion("a4", 11, 20,
                "您在举止仪表、言谈接触，外表看来并无异常表现，但通过该问卷可知，这只是一种愉快乐观的假象。您的内心有痛苦悲观、多思多虑、自卑消极、精力不足、失眠等问题。建议您进一步深入地做精神检查和心理测定。"));

        cDao.insert(new Conclusion("a4", 21, 30,
                "您出现了顽固持久、久治难愈的以失眠为中心睡眠障碍，并且表现出了心境低落，兴趣和愉快感丧失，容易疲劳。通过该问卷结果可知，您极有可能患上了轻度抑郁症，希望您能及时去医院接受心理辅导和治疗。"));

        cDao.insert(new Conclusion("a4", 31, 40,
                "您出现了心情差、情绪提不起来、明显的精神反应迟钝、再有就是出现厌食然后体重因此下降，还有就是罪恶感不断加深，这已经达到了重度抑郁症发作标准，建议您立马去医院接受心理辅导和治疗。"));
    }
}
