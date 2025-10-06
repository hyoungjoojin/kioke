import CreatePageButton from './components/CreatePageButton';
import JournalTitle from './components/JournalTitle';
import type { IconName } from '@/components/ui/icon';
import Icon from '@/components/ui/icon';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { JournalView } from '@/constant/journal';
import dynamic from 'next/dynamic';
import type { ComponentType } from 'react';

const views: {
  [key in JournalView]: {
    icon: IconName;
    title: string;
    content: ComponentType<{
      journalId: string;
    }>;
  };
} = {
  [JournalView.Pages]: {
    icon: 'menu',
    title: 'Pages',
    content: dynamic(() => import('./components/views/JournalPagesView')),
  },
  [JournalView.Gallery]: {
    icon: 'image',
    title: 'Gallery',
    content: dynamic(() => import('./components/views/JournalGalleryView')),
  },
  [JournalView.Map]: {
    icon: 'map',
    title: 'Map',
    content: dynamic(() => import('./components/views/JournalMapView')),
  },
};

export default async function Journal({
  params,
}: {
  params: Promise<{ journalId: string }>;
}) {
  const { journalId } = await params;

  return (
    <>
      <JournalTitle journalId={journalId} />

      <Tabs defaultValue={JournalView.Pages}>
        <TabsList className='flex justify-between w-full'>
          <div className='flex'>
            {Object.values(JournalView).map((view, index) => {
              return (
                <TabsTrigger key={index} value={view}>
                  <div className='flex items-center'>
                    <Icon name={views[view].icon} size={18} />
                    <span className='mx-1'>{views[view].title}</span>
                  </div>
                </TabsTrigger>
              );
            })}
          </div>

          <div>
            <CreatePageButton journalId={journalId} />
          </div>
        </TabsList>

        {Object.values(JournalView).map((view, index) => {
          const page = views[view];

          return (
            <TabsContent key={index} value={view}>
              <page.content journalId={journalId} />
            </TabsContent>
          );
        })}
      </Tabs>
    </>
  );
}
