import JournalTitle from './components/JournalTitle';
import CreatePageButton from './components/controls/CreatePageButton';
import FilterButton from './components/controls/FilterButton';
import PagesCalendar from './components/pages/PagesCalendar';
import PagesList from './components/pages/PagesList';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { getTranslations } from 'next-intl/server';

export default async function Journal({
  params,
}: {
  params: Promise<{ journalId: string }>;
}) {
  const { journalId } = await params;
  const t = await getTranslations();

  return (
    <>
      <JournalTitle journalId={journalId} />

      <Tabs defaultValue='list'>
        <TabsList className='w-full'>
          <div className='flex items-center justify-between w-full'>
            <div className='flex'>
              <TabsTrigger value='list'>
                {t('journal.main.tabs.list')}
              </TabsTrigger>
              <TabsTrigger value='calendar'>
                {t('journal.main.tabs.calendar')}
              </TabsTrigger>
            </div>

            <div>
              <FilterButton />
              <CreatePageButton journalId={journalId} />
            </div>
          </div>
        </TabsList>
        <TabsContent value='list'>
          <PagesList journalId={journalId} />
        </TabsContent>
        <TabsContent value='calendar'>
          <PagesCalendar journalId={journalId} />
        </TabsContent>
      </Tabs>
    </>
  );
}
