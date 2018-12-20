import java.io.File

/**
 * 数字4桁のディレクトリ名から先頭の数字1桁を削除し、数字3桁のディレクトリ名に変更します。
 */
fun main(args: Array<String>) {

    // 対象のディレクトリ名を設定
    val path = """D:\work"""

    File(path).listFiles()
        // 正規表現に合致したディレクトリ名のみ抽出
        .filter { Regex("""[0-9]{4}""").matches(it.name) }
        .forEach {
            // 変更するディレクトリ（Fileクラスで作成）を指定し、名前を変更
            it.renameTo(File(path, it.name.substring(1, it.name.length)))
        }
}
