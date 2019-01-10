import java.io.File


/**
 * エントリポイント
 */
fun main(args: Array<String>) {

    // 対象のディレクトリ名を設定
    val path = """D:\work\md"""

    changeDirectory(path)
    changeImageLink(path)

    changeFileName(path)
    changeImageLink2(path)
}

/**
 * 数字4桁のディレクトリ名から先頭の数字1桁を削除し、数字3桁のディレクトリ名に変更します。
 */
private fun changeDirectory(path: String) {

    val reg = Regex("""0[0-9]{3}""")

    File(path).walkTopDown()
        // 正規表現に合致したディレクトリ名のみ抽出
        .filter {
            it.isDirectory
                    && reg.matches(it.name)
        }
        .forEach {
            // 変更するディレクトリ（Fileクラスで作成）を指定し、名前を変更
            it.renameTo(
                File(
                    // ファイル名の除くパス名
                    it.path.substring(0, it.path.length - 4),
                    // ファイル名から先頭1文字を除いたファイル名
                    it.name.substring(1, it.name.length)
                )
            )
        }
}

/**
 * 数字3桁の画像ファイル名から先頭の数字1桁を削除し、数字2桁のファイル名に変更します。
 */
fun changeFileName(path: String) {

    File(path).walkTopDown()
        .filter { it.isFile && (it.extension == "png" || it.extension == "jpg") }
        .forEach {
            it.renameTo(
                File(
                    it.path.substring(0, it.path.length - 7),
                    it.name.substring(1, it.name.length)
                )
            )
        }
}

/**
 * Markdownファイル内の画像リンクのパスを修正します。
 */
private fun changeImageLink(path: String) {

    val regImageLink = Regex("""!\[.*]\(img/[0-9]{4}/[0-9]{3}.(jpg|png)\)""")
    val regChangeDirName = Regex("""/[0-9]{4}/""")

    val targetFiles = File(path).walkTopDown()
        // 拡張子がmdのファイルを抽出
        .filter { it.isFile && it.extension == "md" }

    val writeLines = mutableListOf<String>()
    for (file in targetFiles) {

        writeLines.clear()

        // 1行ずつループ
        file.forEachLine {
            // 正規表現で部分一致する場合
            if (regImageLink.containsMatchIn(it)) {

                // 必ず1件のみ存在する
                val result = regChangeDirName.findAll(it)
                    .elementAt(0).value

                val text = it.replace(
                    result,
                    "/" + result.substring(2)
                )
                writeLines.add(text)

            } else {
                writeLines.add(it)
            }
        }

        file.writeText(
            writeLines.joinToString(System.getProperty("line.separator"))
                    + System.getProperty("line.separator")
        )
    }
}

/**
 * Markdownファイル内の画像リンクのパスを修正します。
 */
fun changeImageLink2(path: String) {

    val regImageLink = Regex("""!\[.*]\(img/[0-9]{3}/[0-9]{3}.(jpg|png)\)""")
    val regChangeDirName = Regex("""/[0-9]{3}\.""")

    val targetFiles = File(path).walkTopDown()
        .filter { it.isFile && it.extension == "md" }

    val writeLines = mutableListOf<String>()
    for (file in targetFiles) {

        writeLines.clear()

        file.forEachLine {
            // 正規表現で部分一致する場合
            if (regImageLink.containsMatchIn(it)) {

                // 必ず1件のみ存在する
                val result = regChangeDirName.findAll(it)
                    .elementAt(0).value

                val text = it.replace(
                    result,
                    "/" + result.substring(2)
                )
                writeLines.add(text)

            } else {
                writeLines.add(it)
            }
        }

        file.writeText(
            writeLines.joinToString(System.getProperty("line.separator"))
                    + System.getProperty("line.separator")
        )
    }
}
