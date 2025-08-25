import JournalCollectionsList from './components/JournalCollectionsList';
import { getTranslations } from 'next-intl/server';

export default async function JournalCollections() {
  const t = await getTranslations();

  return (
    <>
      <h1 className='text-3xl h-20 flex items-center'>
        {t('journal-collections.title')}
      </h1>

      <div className='relative'>
        <JournalCollectionsList />
      </div>
    </>
  );
}
