import JournalCollectionsList from './components/JournalCollectionsList';
import { getQueryClient } from '@/lib/query';
import { getJournalCollectionsQueryOptions } from '@/query/journal/getJournalCollections';
import { HydrationBoundary, dehydrate } from '@tanstack/react-query';
import { getTranslations } from 'next-intl/server';

export default async function JournalCollections() {
  const t = await getTranslations();

  const queryClient = getQueryClient();
  queryClient.prefetchQuery(getJournalCollectionsQueryOptions());

  return (
    <>
      <h1 className='text-3xl h-20 flex items-center'>
        {t('journal-collections.title')}
      </h1>

      <div className='relative'>
        <HydrationBoundary state={dehydrate(queryClient)}>
          <JournalCollectionsList />
        </HydrationBoundary>
      </div>
    </>
  );
}
