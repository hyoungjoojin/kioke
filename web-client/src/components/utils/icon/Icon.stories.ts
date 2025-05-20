import Icon, { IconName } from './Icon';
import type { Meta, StoryObj } from '@storybook/react';

const meta = {
  title: 'Icon',
  component: Icon,
  parameters: {
    layout: 'centered',
  },
  tags: ['autodocs'],
  argTypes: {},
  args: {},
} satisfies Meta<typeof Icon>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Primary: Story = {
  args: {
    name: IconName.HOME,
  },
};
