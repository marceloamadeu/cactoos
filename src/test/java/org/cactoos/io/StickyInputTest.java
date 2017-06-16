/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.io;

import org.cactoos.Input;
import org.cactoos.TextHasString;
import org.cactoos.func.FuncAsMatcher;
import org.cactoos.func.RepeatedFunc;
import org.cactoos.text.BytesAsText;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link StickyInput}.
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.6
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class StickyInputTest {

    @Test
    public void readsFileContent() {
        MatcherAssert.assertThat(
            "Can't read bytes from a file",
            new StickyInput(
                new ResourceAsInput(
                    "org/cactoos/large-text.txt"
                )
            ),
            new FuncAsMatcher<>(
                new RepeatedFunc<Input, Boolean>(
                    input -> new InputAsBytes(
                        new TeeInput(input, new DeadOutput())
                    // @checkstyle MagicNumber (2 lines)
                    ).asBytes().length == 73471,
                    10
                )
            )
        );
    }

    @Test
    public void readsRealUrl() {
        MatcherAssert.assertThat(
            "Can't fetch text page from the URL",
            new BytesAsText(
                new InputAsBytes(
                    new StickyInput(
                        new UrlAsInput(
                            // @checkstyle LineLength (1 line)
                            "https://raw.githubusercontent.com/yegor256/cactoos/0.5/pom.xml"
                        )
                    )
                )
            ),
            new TextHasString(
                Matchers.endsWith("</project>\n")
            )
        );
    }

}