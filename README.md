## リモコン（Rimokon）
Rimokon 是一款在闲暇时间为我在 yangkeduo 上买的磁吸式 LED 灯的控制器。

它完全使用 Jetpack Compose ＋ Kotlin 开发，并使用 `ConsumerIrManager API` 来发送红外信号。

# 按键映射
我的 LED 灯的信号映射表（标准 NEC 码）：

地址码：0xFF -> 0

命令码：

亮度＋：E01F -> 7

亮度－：906F -> 9

最暖色：02FD -> 64

偏暖色：C23D -> 67

冷　色：22DD -> 68

开／关：A25D -> 69

夜间模式（最低亮度＋偏暖色）：E21D -> 71

## NEC Code to PROMTO RAW
拿到了标准 NEC 码后，你就可以...

# PROMTO RAW TO PULSE ARRAY
我们可以把 `PROMTO RAW` 格式的红外码转换成适用于 `ConsumerIrManager API` 的脉冲数组，我使用下列的 Python 程序来转换：

```python
def pronto_to_ir_pattern(pronto_hex: str):
    codes = [int(x, 16) for x in pronto_hex.split()]
    frequency = 1000000 // (codes[1] * 0.241246)  # 计算载波频率
    sequence = codes[4:]  # 实际的信号数据
    
    pattern = [int(x * (1000000 / frequency)) for x in sequence]
    
    return pattern

if __name__ == "__main__":

    pronto_hex = input("请输入 Pronto 码 (空格分隔的十六进制字符串): ")
    pattern = pronto_to_ir_pattern(pronto_hex)

    
    print("转换后的 Android 红外码数组:")
    print(pattern)
```
