/*
 * Copyright 2022 Tarsin Norbin
 *
 * This file is part of EhViewer
 *
 * EhViewer is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * EhViewer is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with EhViewer.
 * If not, see <https://www.gnu.org/licenses/>.
 */
package com.hippo

import android.os.ParcelFileDescriptor
import java.io.FileDescriptor

object Native {
    fun initialize() {
        System.loadLibrary("ehviewer")
    }

    @JvmStatic
    external fun getFd(fd: FileDescriptor?): Int

    @JvmStatic
    external fun sendfile(from: Int, to: Int)
}

val FileDescriptor.fd
    get() = Native.getFd(this)

infix fun ParcelFileDescriptor.receivefrom(fd: FileDescriptor) {
    Native.sendfile(fd.fd, getFd())
}

infix fun ParcelFileDescriptor.copyTo(fd: FileDescriptor) {
    Native.sendfile(getFd(), fd.fd)
}

infix fun ParcelFileDescriptor.copyTo(fd: ParcelFileDescriptor) {
    Native.sendfile(getFd(), fd.fd)
}
