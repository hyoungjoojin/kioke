import { IconName } from '@/components/ui/icon';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { getTranslations } from 'next-intl/server';
import dynamic from 'next/dynamic';

const tabs = [
  {
    value: 'friends',
    icon: IconName.USER,
    content: dynamic(() => import('./components/FriendsTab')),
  },
  {
    value: 'feed',
    icon: IconName.EDIT,
    content: dynamic(() => import('./components/FeedTab')),
  },
];

export default async function FriendsMain() {
  const t = await getTranslations();

  return (
    <div>
      <Tabs variant='vertical' defaultValue={tabs[0].value}>
        <TabsList>
          {tabs.map((tab, index) => {
            return (
              <TabsTrigger key={index} icon={tab.icon} value={tab.value}>
                {t(`social.tabs.${tab.value}.name`)}
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
