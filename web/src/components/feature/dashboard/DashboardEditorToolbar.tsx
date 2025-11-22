'use client';

import Widgets from '@/components/feature/dashboard/widgets';
import { Button } from '@/components/ui/button';
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from '@/components/ui/popover';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { WidgetType } from '@/constant/dashboard';
import { useDashboardActions } from '@/store/dashboard';
import { v4 as uuidv4 } from 'uuid';

interface WidgetGroup {
  name: string;
  widgets: {
    name: string;
    type: WidgetType;
  }[];
}

const widgetGroups: WidgetGroup[] = [
  {
    name: 'journal',
    widgets: [
      {
        name: 'Add Page',
        type: WidgetType.ADD_PAGE,
      },
      {
        name: 'Journal Cover',
        type: WidgetType.JOURNAL_COVER,
      },
    ],
  },
  {
    name: 'weather',
    widgets: [
      {
        name: 'Weather',
        type: WidgetType.WEATHER,
      },
    ],
  },
];

export default function DashboardEditorToolbar() {
  const { updateDraft } = useDashboardActions();

  const handleInsertWidgetButtonClick = async (type: WidgetType) => {
    const content = await Widgets[type].defaultContent();

    updateDraft((current) => {
      return {
        widgets: [
          ...current.widgets,
          {
            id: uuidv4().toString(),
            type,
            x: 0,
            y: 0,
            content,
          },
        ],
      };
    });
  };

  return (
    <div className='flex justify-between'>
      <div className='flex gap-2'>
        {widgetGroups.map((group, index) => {
          return (
            <Popover key={index}>
              <PopoverTrigger asChild>
                <Button variant='ghost'>{group.name}</Button>
              </PopoverTrigger>
              <PopoverContent
                align={
                  index > (widgetGroups.length * 2) / 3
                    ? 'end'
                    : index > widgetGroups.length / 3
                      ? 'center'
                      : 'start'
                }
                className='min-w-[32rem] min-h-96'
              >
                <Tabs>
                  <TabsList>
                    {group.widgets.map((widget, index) => {
                      return (
                        <TabsTrigger
                          key={index}
                          value={widget.name}
                          className='w-full'
                        >
                          {widget.name}
                        </TabsTrigger>
                      );
                    })}
                  </TabsList>

                  {group.widgets.map((widget, index) => {
                    return (
                      <TabsContent key={index} value={widget.name}>
                        <div className='w-full flex justify-center items-center mb-4'>
                          <WidgetPreview type={widget.type} />
                        </div>

                        <div className='self-end'>
                          <Button
                            onClick={() => {
                              handleInsertWidgetButtonClick(widget.type);
                            }}
                          >
                            Insert
                          </Button>
                        </div>
                      </TabsContent>
                    );
                  })}
                </Tabs>
              </PopoverContent>
            </Popover>
          );
        })}
      </div>
    </div>
  );
}

const WidgetPreview = ({ type }: { type: WidgetType }) => {
  const Widget = Widgets[type].main;
  return <Widget id='' preview type={type} />;
};
