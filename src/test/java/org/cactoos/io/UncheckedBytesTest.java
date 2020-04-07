/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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

import java.io.IOException;
import org.cactoos.Text;
import org.cactoos.text.TextOf;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link UncheckedBytes}.
 *
 * @since 0.3
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class UncheckedBytesTest {

    @Test(expected = RuntimeException.class)
    public void rethrowsCheckedToUncheckedException() {
        new UncheckedBytes(
            () -> {
                throw new IOException("intended");
            }
        ).asBytes();
    }

    @Test
    public void worksNormallyWhenNoExceptionIsThrown() throws Exception {
        final Text source = new TextOf("hello, cactoos!");
        new Assertion<>(
            "Must works normally when no exception is thrown",
            new UncheckedBytes(
                new BytesOf(source)
            ).asBytes(),
            Matchers.equalTo(
                new BytesOf(source).asBytes()
            )
        );
    }
}
