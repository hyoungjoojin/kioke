import { SettingsSubmit } from './components';
import AccountTabContent from './components/AccountTabContent';
import { SettingsProvider } from './components/SettingsContext';
import type { IconName } from '@/components/ui/icon';
import { ScrollArea } from '@/components/ui/scroll-area';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { useTranslations } from 'next-intl';

const tabs: {
  icon: IconName;
  value: string;
  content: React.ReactNode;
}[] = [
  {
    icon: 'user',
    value: 'account',
    content: <AccountTabContent />,
  },
];

export default function SettingsModal() {
  const t = useTranslations();

  return (
    <SettingsProvider>
      <div className='flex h-full w-full'>
        <Tabs
          variant='vertical'
          defaultValue={tabs[0].value}
          className='w-full'
        >
          <TabsList>
            {tabs.map((tab, index) => {
              {
                return (
                  <TabsTrigger key={index} value={tab.value}>
                    {t(`modal.settings.tabs.${tab.value}.name`)}
                  </TabsTrigger>
                );
              }
            })}
          </TabsList>

          <div className='flex flex-col grow'>
            {tabs.map((tab, index) => {
              {
                return (
                  <TabsContent key={index} value={tab.value}>
                    <ScrollArea className='h-full'>{tab.content}</ScrollArea>
                  </TabsContent>
                );
              }
            })}

            <div className='self-end'>
              <SettingsSubmit />
            </div>
          </div>
        </Tabs>
      </div>
    </SettingsProvider>
  );
}
