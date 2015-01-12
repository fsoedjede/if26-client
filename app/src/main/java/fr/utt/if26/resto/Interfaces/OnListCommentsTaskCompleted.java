package fr.utt.if26.resto.Interfaces;

import fr.utt.if26.resto.Adapters.ListCommentsAdapter;

/**
 * Created by soedjede on 26/12/14 for Resto
 */
public interface OnListCommentsTaskCompleted {
    void hydrateListView(ListCommentsAdapter adapter);
}
