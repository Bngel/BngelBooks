package com.example.bngelbooks.logic.model

private val eat_tag_valid = listOf(
    Tag("早餐"),Tag("午饭"),Tag("晚饭"),Tag("超市"),Tag("零食"),Tag("牛奶"),Tag("水"),
        Tag("饮料"),Tag("面包"),Tag("夜宵"),Tag("奶茶"), Tag("水果"),Tag("咖啡")
)

private val traffic_tag_valid = listOf(
    Tag("车费"),Tag("公交"),Tag("打车"),Tag("摩的"),Tag("地铁"),
    Tag("加油"),Tag("过路费"),Tag("高速收费")
)

private val clothes_tag_valid = listOf(
    Tag("鞋子"),Tag("裤子"),Tag("外套"),Tag("内衣"),Tag("袜子"),
    Tag("裙子"),Tag("包包"),Tag("内裤"),Tag("外套"),Tag("睡衣"),
    Tag("毛衣"),Tag("衬衫")
)

private val daily_tag_valid = listOf(
    Tag("纸巾"),Tag("牙膏"),Tag("洗衣液"),Tag("超市"),Tag("卫生巾"),
    Tag("沐浴露"),Tag("牙刷"),Tag("杯子"),Tag("床上用品")
)

private val medical_tag_valid = listOf(
    Tag("挂号"),Tag("药费"),Tag("看病"),Tag("检查"),Tag("保险")
)

private val study_tag_valid = listOf(
    Tag("本子"),Tag("打印"),Tag("文具"),Tag("修正带"),Tag("书籍"),
    Tag("资料"),Tag("报名"),Tag("学费"),Tag("辅导")
)

private val play_tag_valid = listOf(
    Tag("氪金"),Tag("玩具"),Tag("手办"),Tag("皮肤"),Tag("爱好"),
    Tag("相机"),Tag("模型"),Tag("镜头")
)

private val others_tag_valid = listOf(
    Tag("手续费"),Tag("零碎花销"),Tag("其他")
)

fun get_eatTags() = eat_tag_valid

fun get_trafficTags() = traffic_tag_valid

fun get_clothesTags() = clothes_tag_valid

fun get_dailyTags() = daily_tag_valid

fun get_medicalTags() = medical_tag_valid

fun get_studyTags() = study_tag_valid

fun get_playTags() = play_tag_valid

fun get_othersTags() = others_tag_valid