package org.dgkat.multiplatform_fake_shop.core.composables

import androidx.compose.foundation.lazy.LazyListState

internal fun LazyListState.endReached(distanceFromEnd: Int = 1): Boolean {
    val lastVisibleItem = this.layoutInfo.visibleItemsInfo.lastOrNull()
    return lastVisibleItem?.index != 0 && lastVisibleItem?.index == this.layoutInfo.totalItemsCount - distanceFromEnd
}