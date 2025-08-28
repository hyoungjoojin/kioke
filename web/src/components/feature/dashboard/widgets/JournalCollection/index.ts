import JournalCollectionWidgetEditModal from './edit';
import JournalCollectionWidgetPreview from './preview';
import JournalCollectionWidget from './widget';
import { getQueryClient } from '@/lib/query';
import { collectionsQueryOptions } from '@/query/collection';

export interface JournalCollectionWidgetContent {
  collectionId?: string;
}

const JournalCollectionWidgetDefaultContent =
  async (): Promise<JournalCollectionWidgetContent> => {
    const queryClient = getQueryClient();

    return queryClient
      .fetchQuery(collectionsQueryOptions())
      .then((collections) => ({
        collectionId: collections.at(0)?.id,
      }));
  };

export {
  JournalCollectionWidget,
  JournalCollectionWidgetPreview,
  JournalCollectionWidgetEditModal,
  JournalCollectionWidgetDefaultContent,
};
