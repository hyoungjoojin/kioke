import { IconName } from '@/components/ui/icon';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { useTranslations } from 'next-intl';
import dynamic from 'next/dynamic';

const tabs = [
  {
    value: 'account',
    icon: IconName.USER,
    content: dynamic(() => import('./components/ThemeTabContent')),
  },
  {
    value: 'theme',
    icon: IconName.PAINT,
    content: dynamic(() => import('./components/ThemeTabContent')),
  },
  {
    value: 'notifications',
    icon: IconName.BELL,
    content: dynamic(() => import('./components/ThemeTabContent')),
  },
];

export default function SettingsModal() {
  const t = useTranslations();

  return (
    <div className='flex h-full w-full'>
      <Tabs variant='vertical'>
        <TabsList defaultValue={tabs[0].value}>
          {tabs.map((tab, index) => {
            {
              return (
                <TabsTrigger key={index} icon={tab.icon} value={tab.value}>
                  {t(`modal.settings.tabs.${tab.value}.name`)}
                </TabsTrigger>
              );
            }
          })}
        </TabsList>

        {tabs.map((tab, index) => {
          {
            return (
              <TabsContent key={index} value={tab.value}>
                <tab.content />
              </TabsContent>
            );
          }
        })}
      </Tabs>
    </div>
  );
}
