import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import dynamic from 'next/dynamic';

const tabs = [
  {
    value: 'journals',
    title: 'My Journals',
    content: dynamic(() => import('./components/tabs/JournalsTab')),
  },
  {
    value: 'collections',
    title: 'Collections',
    content: dynamic(() => import('./components/tabs/CollectionsTab')),
  },
  {
    value: 'bookmarks',
    title: 'Bookmarks',
    content: dynamic(() => import('./components/tabs/BookmarksTab')),
  },
  {
    value: 'archive',
    title: 'Archive',
    content: dynamic(() => import('./components/tabs/ArchiveTab')),
  },
];

export default async function Journals() {
  return (
    <div>
      <Tabs variant='vertical' defaultValue={tabs[0].value}>
        <TabsList>
          {tabs.map((tab, index) => {
            return (
              <TabsTrigger key={index} value={tab.value}>
                {tab.title}
              </TabsTrigger>
            );
          })}
        </TabsList>

        {tabs.map((tab, index) => {
          return (
            <TabsContent key={index} value={tab.value}>
              <tab.content />
            </TabsContent>
          );
        })}
      </Tabs>
    </div>
  );
}
