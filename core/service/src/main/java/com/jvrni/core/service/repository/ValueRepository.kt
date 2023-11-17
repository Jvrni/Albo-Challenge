package com.jvrni.core.service.repository

import kotlinx.coroutines.flow.Flow

interface ValueRepository {
    fun getValue() : Flow<Int>
}