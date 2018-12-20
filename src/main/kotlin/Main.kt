import java.io.File

/**
 * 数字4桁のディレクトリ名から先頭の数字1桁を削除し、数字3桁のディレクトリ名に変更する。
 */
fun main(args: Array<String>) {
    // 正規表現
    val regex = Regex("""[0-9]{4}""")
    // TODO:対象のディレクトリ名を設定
    val path = """C:\hoge"""

    val file = File(path)
    // 正規表現に合致したディレクトリ名のみ抽出
    val target = file.listFiles().filter { regex.matches(it.name) }
    for (t in target) {
        // 変更するディレクトリ（Fileクラスで作成）
        val dest = File(path, t.name.substring(1, t.name.length))
        // 名前の変更
        t.renameTo(dest)
    }
}
