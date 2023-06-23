package com.example.composeime

import android.content.Context
import android.content.Intent

/**	This class facilitates logging of IME-specific event by the TEMA application.
 *
 * http://www.cse.yorku.ca/~stevenc/tema/
 *
 * @version 1.0 - 05/2014
 * @author Steven J. Castellucci
 */
class TemaImeLogger
/**	Initializes this object.  */(private val context: Context) {
    /**	The character indicating a comment line.  */
    val COMMENT = "[#]"

    /**	The character delimiting the fields.  */
    val DELIM = "\t"

    /**	The key used to retrieve the String being written to the log.  */
    val KEY_1 = "data1"

    /**	The key used to retrieve the isComment boolean flag.  */
    val KEY_2 = "data2"

    /**	The key used to retrieve the timestamp for this event.  */
    val KEY_3 = "data3"
    private val BROADCAST_TEMA = "ca.yorku.cse.tema"

    /**	Writes the passed String to TEMA's IME log.
     * The string can contain multiple fields or represent a comment.
     * Non-comments will be prefixed with a timestamp.
     *
     * @param s the String to write
     * @param isComment if true, prefixes the `COMMENT` String
     */
    fun writeToLog(s: String?, isComment: Boolean) {
        val i = Intent(BROADCAST_TEMA)
        i.putExtra(KEY_1, s)
        i.putExtra(KEY_2, isComment)
        i.putExtra(KEY_3, System.currentTimeMillis())
        context.sendBroadcast(i)
    }
}