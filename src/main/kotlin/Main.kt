import java.io.File


/**
 * エントリポイント
 */
fun main(args: Array<String>) {

    // 対象のディレクトリ名を設定
    val path = """D:\work\md"""

    // TODO:changeDirectory(path)
    changeImageLink(path)
}

/**
 * 数字4桁のディレクトリ名から先頭の数字1桁を削除し、数字3桁のディレクトリ名に変更します。
 */
private fun changeDirectory(path: String) {

    File(path).listFiles()
        // 正規表現に合致したディレクトリ名のみ抽出
        .filter { Regex("""[0-9]{4}""").matches(it.name) }
        .forEach {
            // 変更するディレクトリ（Fileクラスで作成）を指定し、名前を変更
            it.renameTo(File(path, it.name.substring(1, it.name.length)))
        }
}

/**
 * Markdownファイル内の画像リンクのパスを修正します。
 */
private fun changeImageLink(path: String) {

    // 指定したパス内の、全てのディレクトリ、ファイルを取得
    File(path).walkTopDown()
        // 拡張子がmdのファイルを抽出
        .filter { it.isFile && it.extension == "md" }
        .forEach {
            it.forEachLine {
            }
        }
}