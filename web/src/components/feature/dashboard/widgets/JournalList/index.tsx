import JournalListEdit from './edit';
import JournalListPreview from './preview';
import JournalList from './widget';
import { getQueryClient } from '@/lib/query';
import { collectionsQueryOptions } from '@/query/collection';

interface JournalListWidgetContent {
  collectionId?: string;
}

const initial = async (): Promise<JournalListWidgetContent> => {
  const queryClient = getQueryClient();

  return queryClient
    .fetchQuery(collectionsQueryOptions())
    .then((collections) => ({
      collectionId: collections.at(0)?.id,
    }));
};

export {
  initial as JournalListInitial,
  JournalList,
  JournalListPreview,
  JournalListEdit,
};
